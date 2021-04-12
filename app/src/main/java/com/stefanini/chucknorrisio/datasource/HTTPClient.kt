package com.stefanini.chucknorrisio.datasource

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HTTPClient {

    companion object{
        @JvmStatic
        fun retrofit(): Retrofit{
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ChuckNorrisAPI.BASE_URL)
                .build()
        }
    }
}