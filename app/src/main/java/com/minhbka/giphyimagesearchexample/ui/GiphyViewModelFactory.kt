package com.minhbka.giphyimagesearchexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository

@Suppress("UNCHECKED_CAST")
class GiphyViewModelFactory(
    private val repository: GiphyRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GiphyViewModel(repository) as T
    }
}