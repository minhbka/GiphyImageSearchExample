package com.minhbka.giphyimagesearchexample.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.network.responses.GiphyResponse
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import com.minhbka.giphyimagesearchexample.utils.ApiException
import com.minhbka.giphyimagesearchexample.utils.Coroutines
import com.minhbka.giphyimagesearchexample.utils.NoInternetException
import kotlinx.coroutines.*
const val  STATUS_OK_CODE = 200
class GiphyViewModel(
    private val repository: GiphyRepository
):ViewModel() {
    private lateinit var jobSearch: Job
    private lateinit var jobFavor:Job

    private val _images = MutableLiveData<List<GiphyImage>>()
    var giphyListener : GiphyListener? = null

    val images : LiveData<List<GiphyImage>>
        get() = _images

    val favorImages : LiveData<List<GiphyImage>>
        get() = getFavorImage()

    fun getSearchImage(){
        giphyListener?.onStarted()

        jobSearch = Coroutines.io{
            try {
                val searchResult = repository.getSearchGiphyImage()
                if (searchResult.meta.status == STATUS_OK_CODE){
                    val listImage = searchResult.data.map {
                        GiphyImage(it.id, it.images.original.url, repository.getFavorGiphyImageById(it.id) != null)
                    }
                    _images.postValue(listImage)
                    Coroutines.main{
                        giphyListener?.onSuccess()
                    }
                    return@io
                }
                else {
                    Coroutines.main{
                        giphyListener?.onFailure(searchResult.meta.msg)
                    }

                }
            }
            catch (e: ApiException){
                Coroutines.main {
                    giphyListener?.onFailure(e.message!!)
                }
            }
            catch (e: NoInternetException){
                Coroutines.main {
                    giphyListener?.onFailure(e.message!!)
                }
            }
        }

    }



    fun getFavorImage() = repository.getFavorGiphyImage()

    fun onFavoriteButtonClick(image:GiphyImage){

        image.is_favourite = !image.is_favourite
        _images.value = images.value
        if (image.is_favourite){
            repository.saveFavorGiphyImage(image)
        }
        else{
            repository.deleteFavorGiphyImage(image)
        }
    }



    override fun onCleared() {
        super.onCleared()
        if(::jobSearch.isInitialized) jobSearch.cancel()
        if(::jobFavor.isInitialized) jobFavor.cancel()
    }

}