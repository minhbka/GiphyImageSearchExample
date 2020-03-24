package com.minhbka.giphyimagesearchexample.ui

interface GiphyListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message:String)
}