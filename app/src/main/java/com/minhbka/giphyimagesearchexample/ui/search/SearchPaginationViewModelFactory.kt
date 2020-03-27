package com.minhbka.giphyimagesearchexample.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Suppress("UNCHECKED_CAST")
class SearchPaginationViewModelFactory (
    private val repository: GiphyRepository
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchPaginationViewModel(repository) as T
    }
}
