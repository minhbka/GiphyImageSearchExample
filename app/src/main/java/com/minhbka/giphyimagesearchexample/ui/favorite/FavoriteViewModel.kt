package com.minhbka.giphyimagesearchexample.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository


class FavoriteViewModel(
    private val repository: GiphyRepository
):ViewModel() {

    val favorImages : LiveData<List<GiphyImage>>
        get() = getFavorImage()


    fun getFavorImage() = repository.getFavorGiphyImage()

    fun onFavoriteButtonClick(image:GiphyImage){

        image.is_favourite = !image.is_favourite
        if (image.is_favourite){
            repository.saveFavorGiphyImage(image)
        }
        else{
            repository.deleteFavorGiphyImage(image)
        }
    }


}