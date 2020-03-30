package com.minhbka.giphyimagesearchexample.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.repository.GiphyImagesDataSource
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import com.minhbka.giphyimagesearchexample.utils.LoadingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@FlowPreview
@ExperimentalCoroutinesApi
class SearchPaginationViewModel(
        private val repository: GiphyRepository
    ) : ViewModel() {

    private var progressLoadStatus : LiveData<LoadingStatus>
    private val mutableLiveData = MutableLiveData<GiphyImagesDataSource>()

    private var giphyImagesLiveData  = initialGiphySearchListLiveData()
    private var giphyImagesDataSource: GiphyImagesDataSource? = null
    val queryChannel = ConflatedBroadcastChannel<String>()
    init {

        queryChannel.asFlow()
            .debounce(QUERY_DEBOUNCE)
            .onEach { giphyImagesDataSource?.invalidate()}
            .launchIn(CoroutineScope(Dispatchers.IO))

        progressLoadStatus = Transformations.switchMap(mutableLiveData){
            it.progressLiveStatus
        }
    }
    private fun initialGiphySearchListLiveData(): LiveData<PagedList<GiphyImage>>{

        val config = PagedList.Config.Builder()
            .setPageSize(SEARCH_RESULT_LIMIT)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(false)
            .build()
        val dataSource = object : DataSource.Factory<Int, GiphyImage>(){
            override fun create(): GiphyImagesDataSource {
                return GiphyImagesDataSource(
                    repository = repository,
                    query = queryChannel.valueOrNull.orEmpty(),
                    scope = CoroutineScope(Dispatchers.IO)
                ).also {
                    giphyImagesDataSource = it
                    mutableLiveData.postValue(giphyImagesDataSource)

                }

            }

        }

        return LivePagedListBuilder(dataSource, config).build()
    }

    fun getProgressLoadStatus():LiveData<LoadingStatus>{
        return progressLoadStatus
    }

    fun getGiphyImagesLiveData() : LiveData<PagedList<GiphyImage>>{
        return giphyImagesLiveData
    }


    fun onFavoriteButtonClick(image:GiphyImage){
        image.is_favourite = !image.is_favourite
        val livaDataValue = giphyImagesLiveData.value
        val item = livaDataValue?.find {
            it.id == image.id
        }
        item?.is_favourite = image.is_favourite

        if (image.is_favourite){
            repository.saveFavorGiphyImage(image)
        }
        else{
            repository.deleteFavorGiphyImage(image)
        }
    }

    companion object {
        const val SEARCH_RESULT_LIMIT = 20
        private const val QUERY_DEBOUNCE = 200L
        private const val PREFETCH_DISTANCE = 10
    }

}
