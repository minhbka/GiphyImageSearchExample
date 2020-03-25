package com.minhbka.giphyimagesearchexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.minhbka.giphyimagesearchexample.data.db.AppDatabase
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.network.GiphyApi
import com.minhbka.giphyimagesearchexample.network.SafeApiRequest
import com.minhbka.giphyimagesearchexample.network.responses.GiphyResponse
import com.minhbka.giphyimagesearchexample.utils.ApiException
import com.minhbka.giphyimagesearchexample.utils.Coroutines
import com.minhbka.giphyimagesearchexample.utils.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GiphyRepository (
    private val api:GiphyApi,
    private val db:AppDatabase


):SafeApiRequest(){

    suspend fun getSearchGiphyImage()= apiRequest {api.getSearch(query = "cherry blossom", offset = 0)}
    suspend fun getFavorGiphyImage(): LiveData<List<GiphyImage>>{
        return withContext(Dispatchers.IO){
            db.getFavorGiphyImageDao().getAllFavorImage()
        }
    }
}