package com.stefanini.chucknorrisio.model

import com.google.gson.annotations.SerializedName

class Joke(iconUrl: String?, value: String?) {

    @SerializedName("icon_url")
    var iconUrl: String? = iconUrl

    @SerializedName("value")
    var value: String? = value

}