package com.minhbka.giphyimagesearchexample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.minhbka.giphyimagesearchexample.R
import com.minhbka.giphyimagesearchexample.databinding.RecycleviewImageBinding
import com.minhbka.giphyimagesearchexample.network.responses.ImageObject

class GiphyImagesAdapter(
    private val images:List<ImageObject>
):RecyclerView.Adapter<GiphyImagesAdapter.ImagesViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder =
        ImagesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycleview_image,
                parent,
                false
            )
        )

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.recycleviewImageBinding.image = images[position]
    }

    inner class ImagesViewHolder(
        val recycleviewImageBinding: RecycleviewImageBinding
    ):RecyclerView.ViewHolder(recycleviewImageBinding.root)

}