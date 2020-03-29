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

){
    override fun equals(other:Any?):Boolean {
        if (javaClass != other?.javaClass)
            return false

        other as GiphyImage
        return (id == other.id && image_url == other.image_url && is_favourite ==  other.is_favourite)
    }

    override fun toString(): String {
        return "Image: $id, $image_url, $is_favourite"
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + image_url.hashCode()
        result = 31 * result + is_favourite.hashCode()
        return result
    }
}