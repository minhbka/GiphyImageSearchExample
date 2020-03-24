package com.minhbka.giphyimagesearchexample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minhbka.giphyimagesearchexample.network.responses.ImageObject
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import com.minhbka.giphyimagesearchexample.utils.Coroutines
import kotlinx.coroutines.Job

class GiphyViewModel(
    private val repository: GiphyRepository
):ViewModel() {
    private lateinit var job: Job
    private val _images = MutableLiveData<List<ImageObject>>()
    val images : LiveData<List<ImageObject>>
        get() = _images

    fun getSearchImage(){
        job = Coroutines.ioThenMain(
            {repository.getGiphySearchImage()},
            {_images.value = it?.data?.map {giphyOject->
                giphyOject.images.original
            }}
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }

}