package com.minhbka.giphyimagesearchexample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage

@Dao
interface FavorGiphyImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorGiphyImage: GiphyImage)

    @Query("DELETE from favor_images where id= :id")
    fun deleteFavorImgById(id:String)

    @Query("Select * from favor_images")
    fun getAllFavorImage() : LiveData<List<GiphyImage>>

    @Query("Select * from favor_images Where id= :id")
    fun getFavorImgById(id: String): GiphyImage?

}