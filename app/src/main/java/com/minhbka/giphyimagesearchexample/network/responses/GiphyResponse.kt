package com.minhbka.giphyimagesearchexample.network.responses


data class GiphyResponse(
    val data: List<GiphyObject>,
    val meta: Meta,
    val pagination: Pagination
)