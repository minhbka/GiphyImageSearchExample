package com.minhbka.giphyimagesearchexample.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("image")
fun loadImage(view:ImageView, url:String){
    Glide.with(view)
        .asGif()
        .load(url)
        .listener(object : RequestListener<GifDrawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<GifDrawable>?,
                isFirstResource: Boolean
            ): Boolean {

                view.snackbar("Failed to load image.\nMake sure you have an active data connection")
                return false
            }

            override fun onResourceReady(
                resource: GifDrawable?,
                model: Any?,
                target: Target<GifDrawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }


        })
        .into(view)
}

@BindingAdapter("selected")
fun setFavorImage(view:ImageView, is_favorite:Boolean){
    view.isSelected = is_favorite
}