package com.stefanini.chucknorrisio.datasource

import android.os.AsyncTask
import android.util.JsonReader
import android.util.Log
import retrofit2.Call
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class CategoryRemoteDataSource {

    interface ListCategoriesCallback{
        fun onSucess(response: List<String>?)

        fun onError(message: String?)

        fun onComplete()
    }

    fun findAll(callback: ListCategoriesCallback){
        HTTPClient.retrofit().create(ChuckNorrisAPI::class.java)
            .findAll()
            .enqueue(object : retrofit2.Callback<List<String>>{
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    callback.onError(t.message)
                    callback.onComplete()
                }

                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                   if (response.isSuccessful)
                       callback.onSucess(response.body())

                    callback.onComplete()
                }

            })

        //CategoryTask(callback).execute()
    }

    class CategoryTask(var callback: ListCategoriesCallback) : AsyncTask<Void, Void, List<String>>() {

         var errorMessage: String? = null

        override fun doInBackground(vararg params: Void): List<String> {
            val response: MutableList<String> = ArrayList()

            var urlConnection: HttpsURLConnection? = null

            try {
                val url: URL = URL(Endpoint.GET_CATEGORIES)
                urlConnection = url.openConnection() as HttpsURLConnection

                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                val responseCode: Int = urlConnection.responseCode

                if (responseCode > 400){
                        throw IOException("Erro na comunicação do servidor")
                    }

                val inp: InputStream = BufferedInputStream(urlConnection.inputStream)

                val jsonReader = JsonReader(InputStreamReader(inp))

                jsonReader.beginArray()

                while (jsonReader.hasNext()){
                    response.add(jsonReader.nextString())
                }
                jsonReader.endArray()

            }catch (e: MalformedURLException){
                errorMessage = e.message
            }catch (e: IOException){
                errorMessage = e.message
            }

            return response
        }


        override fun onPostExecute(result: List<String>) {
            if (errorMessage != null){
                Log.i("TESTE", errorMessage)
                callback.onError(errorMessage!!)
            }else{
                Log.i("TESTE", result.toString())
                callback.onSucess(result)
            }
            callback.onComplete()
        }
    }


}