package com.minhbka.giphyimagesearchexample.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.minhbka.giphyimagesearchexample.data.db.AppDatabase
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.network.GiphyApi
import com.minhbka.giphyimagesearchexample.repository.GiphyImagesDataSource
import com.minhbka.giphyimagesearchexample.utils.Coroutines

class SearchPaginationViewModel(
    private val api: GiphyApi,
    private val db: AppDatabase
    ) : ViewModel() {
    private var giphyImagesLiveData:LiveData<PagedList<GiphyImage>>
    init {
        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(false)
            .build()
        giphyImagesLiveData = initializedPagedListBuilder(config).build()
    }

    fun getGiphyImages():LiveData<PagedList<GiphyImage>> = giphyImagesLiveData
    private fun initializedPagedListBuilder(config: PagedList.Config):LivePagedListBuilder<Int, GiphyImage>{
        val dataSourceFactory = object :DataSource.Factory<Int, GiphyImage>(){
            override fun create(): DataSource<Int, GiphyImage> {
                return GiphyImagesDataSource(api, db)
            }

        }
        return LivePagedListBuilder<Int, GiphyImage>(dataSourceFactory, config)
    }

    fun onFavoriteButtonClick(image:GiphyImage){
        image.is_favourite = !image.is_favourite
        val livaDataValue = giphyImagesLiveData.value
        val item = livaDataValue?.find {
            it.id == image.id
        }
        item?.is_favourite = image.is_favourite
        //giphyImagesLiveData.value = livaDataValue
        if (image.is_favourite){
            Coroutines.io {
                db.getFavorGiphyImageDao().insert(image)
            }

        }
        else{
            Coroutines.io {
                db.getFavorGiphyImageDao().deleteFavorImgById(image.id)
            }
        }
    }
}
