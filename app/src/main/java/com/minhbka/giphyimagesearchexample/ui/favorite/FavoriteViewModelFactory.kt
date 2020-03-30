package com.minhbka.giphyimagesearchexample.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository

@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory(
    private val repository: GiphyRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repository) as T
    }
}