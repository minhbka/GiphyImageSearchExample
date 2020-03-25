package com.minhbka.giphyimagesearchexample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.network.responses.GiphyResponse
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import com.minhbka.giphyimagesearchexample.utils.ApiException
import com.minhbka.giphyimagesearchexample.utils.Coroutines
import com.minhbka.giphyimagesearchexample.utils.NoInternetException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class GiphyViewModel(
    private val repository: GiphyRepository
):ViewModel() {
    private lateinit var jobSearch: Job
    private lateinit var jobFavor:Job
    private val _favorImages = MutableLiveData<List<GiphyImage>>()
    private val _images = MutableLiveData<List<GiphyImage>>()
    var giphyListener : GiphyListener? = null

    val images : LiveData<List<GiphyImage>>
        get() = _images

    val favorImages : LiveData<List<GiphyImage>>
        get() = _favorImages

    fun getSearchImage(){
        giphyListener?.onStarted()

        jobSearch = Coroutines.ioThenMain(
            {
                try {
                    repository.getSearchGiphyImage()

                }
                catch (e: ApiException){
                    giphyListener?.onFailure(e.message!!)
                }
                catch (e: NoInternetException){
                    giphyListener?.onFailure(e.message!!)
                }
            },
            {

                if (it is GiphyResponse) {

                    _images.value = it.data.map { giphyObject ->
                        GiphyImage(giphyObject.id, giphyObject.images.original.url, false)
                    }
                    giphyListener?.onSuccess()


                }

            }
        )


    }

    fun getFavorImage(){
        jobFavor = Coroutines.ioThenMain(
            {repository.getFavorGiphyImage()},
            {
                _favorImages.value = it?.value
            }
        )
    }
    override fun onCleared() {
        super.onCleared()
        if(::jobSearch.isInitialized) jobSearch.cancel()
        if(::jobFavor.isInitialized) jobFavor.cancel()
    }

}