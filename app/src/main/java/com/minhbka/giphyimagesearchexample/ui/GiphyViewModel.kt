package com.minhbka.giphyimagesearchexample.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.network.responses.GiphyResponse
import com.minhbka.giphyimagesearchexample.network.responses.ImageObject
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import com.minhbka.giphyimagesearchexample.utils.ApiException
import com.minhbka.giphyimagesearchexample.utils.Coroutines
import com.minhbka.giphyimagesearchexample.utils.NoInternetException
import kotlinx.coroutines.Job

class GiphyViewModel(
    private val repository: GiphyRepository
):ViewModel() {
    private lateinit var job: Job
    private val _images = MutableLiveData<List<GiphyImage>>()
    var giphyListener : GiphyListener? = null

    val images : LiveData<List<GiphyImage>>
        get() = _images

    fun getSearchImage(){
        giphyListener?.onStarted()
        job = Coroutines.ioThenMain(
            {
                try {
                    repository.getGiphySearchImage()
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
                else return@ioThenMain

            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }

}