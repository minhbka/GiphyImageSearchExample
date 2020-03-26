package com.minhbka.giphyimagesearchexample.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.minhbka.giphyimagesearchexample.R
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.databinding.RecycleviewImageBinding

class GiphyImagesAdapter(private val listener: RecycleViewClickListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GiphyImage>() {

        override fun areItemsTheSame(oldItem: GiphyImage, newItem: GiphyImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GiphyImage, newItem: GiphyImage): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return GiphyImageViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycleview_image,
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GiphyImageViewHolder -> {
                holder.recycleviewImageBinding.image = differ.currentList[position]
                holder.recycleviewImageBinding.imageViewFavor.setOnClickListener {
                    listener?.onRecyclerViewItemClick(holder.recycleviewImageBinding.imageViewFavor,
                        differ.currentList[position]
                    )
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<GiphyImage>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    class GiphyImageViewHolder(
        val recycleviewImageBinding: RecycleviewImageBinding
    ) : RecyclerView.ViewHolder(recycleviewImageBinding.root)

}


