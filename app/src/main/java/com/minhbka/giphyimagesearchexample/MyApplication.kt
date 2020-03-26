package com.minhbka.giphyimagesearchexample

import android.app.Application
import com.minhbka.giphyimagesearchexample.data.db.AppDatabase
import com.minhbka.giphyimagesearchexample.network.GiphyApi
import com.minhbka.giphyimagesearchexample.network.NetworkConnectionInterceptor
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import com.minhbka.giphyimagesearchexample.ui.GiphyViewModelFactory
import com.minhbka.giphyimagesearchexample.ui.search.SearchPaginationViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { GiphyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { GiphyRepository(instance(), instance()) }
        bind() from provider { GiphyViewModelFactory(instance()) }

        bind() from provider { SearchPaginationViewModelFactory(instance(), instance()) }
    }
}