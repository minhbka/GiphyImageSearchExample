package com.minhbka.giphyimagesearchexample.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus


class GiphyImagesDataSource(
    private val query:String,
    private val repository: GiphyRepository,
    private val scope: CoroutineScope

):PageKeyedDataSource<Int, GiphyImage>(){

    private val _progressLiveStatus = MutableLiveData<LoadingStatus>()
    val progressLiveStatus : LiveData<LoadingStatus>
        get() = _progressLiveStatus

    private val job = Job()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GiphyImage>) {
        (scope + job).launch {
            try {

                _progressLiveStatus.postValue(LoadingStatus(LOADING, "Loading..."))
                val response = repository.getSearchGiphyImage(query = query, limit = params.requestedLoadSize, offset = 0)
                if (response.meta.status == STATUS_OK_CODE){
                    val data = response.data
                    val searchResult = data.map {
                        GiphyImage(it.id, it.images.original.url, repository.getFavorGiphyImageById(it.id) != null)
                    }
                    callback.onResult(searchResult, null, response.pagination.offset+params.requestedLoadSize)
                    _progressLiveStatus.postValue(LoadingStatus(SUCCESS, "Successfully Loaded") )
                }
                else{
                    _progressLiveStatus.postValue(LoadingStatus(FAILURE, response.meta.msg) )
                }
            }
            catch (e: ApiException){
                _progressLiveStatus.postValue(LoadingStatus(FAILURE, e.message!!) )
                Log.d("DEBUG", "loadInitial Exception: ${e.message}")

            }
            catch (e: NoInternetException){
                Log.d("DEBUG", "loadInitial Exception: ${e.message}")
                _progressLiveStatus.postValue(LoadingStatus(FAILURE, e.message!!) )
            }

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GiphyImage>) {
        (scope + job).launch {
            try {
                _progressLiveStatus.postValue(LoadingStatus(LOADING, "Loading..."))
                val response =
                    repository.getSearchGiphyImage(query = query, limit = params.requestedLoadSize, offset = params.key)

                if (response.meta.status == STATUS_OK_CODE) {
                    val data = response.data
                    val searchResult = data.map {
                        GiphyImage(
                            it.id,
                            it.images.original.url,
                            repository.getFavorGiphyImageById(it.id) != null
                        )
                    }
                    callback.onResult(searchResult, response.pagination.offset + params.requestedLoadSize)
                    _progressLiveStatus.postValue(LoadingStatus(SUCCESS, "Successfully Loaded") )
                }
                else{
                    _progressLiveStatus.postValue(LoadingStatus(FAILURE, response.meta.msg) )
                }
            }
            catch (e: ApiException){
                Log.d("DEBUG", "loadAfter Exception: ${e.message}")
                _progressLiveStatus.postValue(LoadingStatus(FAILURE, e.message!!) )

            }
            catch (e: NoInternetException){
                Log.d("DEBUG", "loadAfter Exception: ${e.message}")
                _progressLiveStatus.postValue(LoadingStatus(FAILURE, e.message!!) )
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