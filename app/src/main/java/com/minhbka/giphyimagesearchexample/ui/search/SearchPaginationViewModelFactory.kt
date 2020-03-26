package com.minhbka.giphyimagesearchexample.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minhbka.giphyimagesearchexample.data.db.AppDatabase
import com.minhbka.giphyimagesearchexample.network.GiphyApi

@Suppress("UNCHECKED_CAST")
class SearchPaginationViewModelFactory (
    private val api: GiphyApi,
    private val db: AppDatabase
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchPaginationViewModel(api,db) as T
    }
}
