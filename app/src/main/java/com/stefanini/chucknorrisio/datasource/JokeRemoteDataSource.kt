package com.stefanini.chucknorrisio.datasource

import android.os.AsyncTask
import android.util.JsonReader
import android.util.JsonToken
import com.stefanini.chucknorrisio.model.Joke
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class JokeRemoteDataSource {

    interface JokeCallback{
        fun onSucess(response: Joke?)

        fun onError(message: String?)

        fun onComplete()
    }

    fun findJokeBy(callback: JokeCallback, category: String){
        HTTPClient.retrofit().create(ChuckNorrisAPI::class.java)
            .findRandomBy(category)
            .enqueue(object : Callback<Joke>{
                override fun onFailure(call: Call<Joke>, t: Throwable) {
                    callback.onError(t.message)
                    callback.onComplete()
                }

                override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                    if (response.isSuccessful)
                        callback.onSucess(response.body())

                    callback.onComplete()
                }

            })

       // JokeTask(callback, category).execute()
    }

    class JokeTask(var callback: JokeCallback,var category: String) : AsyncTask<Void, Void, Joke>() {

        var errorMessage: String? = null

        override fun doInBackground(vararg p0: Void?): Joke? {
            var joke: Joke? = null

            var urlConnection: HttpsURLConnection? = null

            try {
                val endpoint: String = String.format("%s?category=%s", Endpoint.GET_JOKE, category)
                val url: URL = URL(endpoint)
                urlConnection = url.openConnection() as HttpsURLConnection

                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                val responseCode: Int = urlConnection.responseCode

                if (responseCode > 400){
                    throw IOException("Erro na comunicação do servidor")
                }

                val inp: InputStream = BufferedInputStream(urlConnection.inputStream)

                val jsonReader = JsonReader(InputStreamReader(inp))

                jsonReader.beginObject()

                var iconUrl: String? = null
                var value: String? = null

                while (jsonReader.hasNext()){
                   val token = jsonReader.peek()

                    if (token == JsonToken.NAME){
                        val name = jsonReader.nextName()

                        if (name.equals("category"))
                            jsonReader.skipValue()
                        else if(name.equals("icon_url"))
                            iconUrl = jsonReader.nextString()
                        else if(name.equals("value"))
                            value = jsonReader.nextString()
                        else
                            jsonReader.skipValue()
                    }
                }

                joke = Joke(iconUrl, value)
                jsonReader.endObject()

            }catch (e: MalformedURLException){
                errorMessage = e.message
            }catch (e: IOException){
                errorMessage = e.message
            }

            return joke
        }

        override fun onPostExecute(joke: Joke) {
            if (errorMessage != null){
                callback.onError(errorMessage!!)
            }else{
                callback.onSucess(joke)
            }
            callback.onComplete()
        }

    }
}