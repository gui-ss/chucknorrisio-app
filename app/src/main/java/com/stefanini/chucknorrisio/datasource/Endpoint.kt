package com.stefanini.chucknorrisio.datasource

class Endpoint {

    companion object{
        const val BASE_URL: String = "https://api.chucknorris.io/"
        const val GET_CATEGORIES = BASE_URL + "jokes/categories"
        const val GET_JOKE = BASE_URL + "jokes/random"

    }

}