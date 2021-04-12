package com.stefanini.chucknorrisio.datasource

import com.stefanini.chucknorrisio.model.Joke
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChuckNorrisAPI {

    companion object {
    const val BASE_URL: String = "https://api.chucknorris.io/"
    }

    @GET("jokes/categories")
    fun findAll(): Call<List<String>>

    @GET("jokes/random")
    fun findRandomBy(@Query("category") category: String?): Call<Joke>
}