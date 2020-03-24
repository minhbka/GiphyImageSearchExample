package com.minhbka.giphyimagesearchexample.repository

import com.minhbka.giphyimagesearchexample.network.GiphyApi
import com.minhbka.giphyimagesearchexample.network.SafeApiRequest

class GiphyRepository (
    private val api:GiphyApi
):SafeApiRequest(){
    suspend fun getGiphySearchImage()= apiRequest {api.getSearch(query = "cherry blossom", offset = 0)  }
}