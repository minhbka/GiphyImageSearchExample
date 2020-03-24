package com.minhbka.giphyimagesearchexample.network

import com.minhbka.giphyimagesearchexample.network.responses.GiphyResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "AZW17XFgJaPJzYbdEJ4HHShB0tLB8U17"
const val BASE_URL = "https://api.giphy.com/v1/gifs/"
interface GiphyApi {

    @GET("search")
    suspend fun getSearch(
        @Query("api_key")apiKey:String = API_KEY,
        @Query("q") query: String,
        @Query("limit") limit: Int? = 5,
        @Query("offset") offset: Int?
    ) : Response<GiphyResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ):GiphyApi{
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(GiphyApi::class.java)
        }
    }

}