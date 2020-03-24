package com.minhbka.giphyimagesearchexample.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favor_images")
data class GiphyImage (
    @PrimaryKey(autoGenerate = false)
    @NonNull
    val id:String,
    var image_url : String,
    var is_favourite : Boolean
)