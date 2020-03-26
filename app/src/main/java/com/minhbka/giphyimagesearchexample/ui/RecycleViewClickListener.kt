package com.minhbka.giphyimagesearchexample.ui

import android.view.View
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage

interface RecycleViewClickListener {
    fun onRecyclerViewItemClick(view: View, image:GiphyImage )
}