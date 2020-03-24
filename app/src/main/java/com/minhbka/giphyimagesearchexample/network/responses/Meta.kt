package com.minhbka.giphyimagesearchexample.network.responses


import com.google.gson.annotations.SerializedName

data class Meta(
    val msg: String,
    @SerializedName("response_id")
    val responseId: String,
    val status: Int
)