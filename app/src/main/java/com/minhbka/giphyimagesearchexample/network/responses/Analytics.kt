package com.minhbka.giphyimagesearchexample.network.responses


import com.google.gson.annotations.SerializedName

data class Analytics(
    val onclick: OnEvent,
    val onload: OnEvent,
    val onsent: OnEvent
)