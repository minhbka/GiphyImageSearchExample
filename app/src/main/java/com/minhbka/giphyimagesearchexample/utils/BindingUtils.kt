package com.minhbka.giphyimagesearchexample.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image")
fun loadImage(view:ImageView, url:String){
    Glide.with(view)
        .asGif()
        .load(url)
        .into(view)
}