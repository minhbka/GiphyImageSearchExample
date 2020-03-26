package com.minhbka.giphyimagesearchexample.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.minhbka.giphyimagesearchexample.R
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.databinding.RecycleviewImageBinding
import com.minhbka.giphyimagesearchexample.ui.RecycleViewClickListener

class GiphyImagesPagedListAdapter(private val listener: RecycleViewClickListener? = null) : PagedListAdapter<GiphyImage, GiphyImagesPagedListAdapter.MyViewHolder>(DiffUtilCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycleview_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.recycleviewImageBinding.image = getItem(position)
        holder.recycleviewImageBinding.imageViewFavor.setOnClickListener {
            listener?.onRecyclerViewItemClick(holder.recycleviewImageBinding.imageViewFavor,
                getItem(position)!!
            )
        }

    }

    override fun submitList(pagedList: PagedList<GiphyImage>?) {
        super.submitList(pagedList)
        notifyDataSetChanged()
    }

    class MyViewHolder(
        val recycleviewImageBinding: RecycleviewImageBinding
    ) : RecyclerView.ViewHolder(recycleviewImageBinding.root)
}

class DiffUtilCallBack : DiffUtil.ItemCallback<GiphyImage>() {
    override fun areItemsTheSame(oldItem: GiphyImage, newItem: GiphyImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GiphyImage, newItem: GiphyImage): Boolean {
        return oldItem.id == newItem.id
                && oldItem.is_favourite == newItem.is_favourite
                && oldItem.image_url == newItem.image_url
    }

}