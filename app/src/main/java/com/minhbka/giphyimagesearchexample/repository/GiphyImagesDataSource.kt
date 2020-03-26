package com.minhbka.giphyimagesearchexample.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.minhbka.giphyimagesearchexample.data.db.AppDatabase
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.network.GiphyApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GiphyImagesDataSource(
    private val api: GiphyApi,
    private val db: AppDatabase
):PageKeyedDataSource<Int, GiphyImage>(){
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO +job)
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GiphyImage>) {
        scope.launch {
            try {
                val response = api.getSearch(limit = params.requestedLoadSize, offset = 0)
                when{
                    response.isSuccessful ->{
                        val data = response.body()?.data
                        val searchResult = data?.map {
                            GiphyImage(it.id, it.images.original.url, db.getFavorGiphyImageDao().getFavorImgById(it.id) !=null)
                        }
                        callback.onResult(searchResult ?: listOf(), response.body()?.pagination?.offset,response.body()?.pagination?.totalCount)
                    }
                }
            }
            catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GiphyImage>) {
        scope.launch {
            try {
                val response = api.getSearch(limit = params.requestedLoadSize, offset = params.key)
                when {
                    response.isSuccessful -> {
                        val data = response.body()?.data
                        val searchResult = data?.map {
                            GiphyImage(
                                it.id,
                                it.images.original.url,
                                db.getFavorGiphyImageDao().getFavorImgById(it.id) != null
                            )
                        }
                        callback.onResult(searchResult ?: listOf(),response.body()?.pagination?.offset)
                    }
                }
            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GiphyImage>) {

    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}