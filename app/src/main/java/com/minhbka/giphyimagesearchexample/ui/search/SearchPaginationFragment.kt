package com.minhbka.giphyimagesearchexample.ui.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.minhbka.giphyimagesearchexample.R
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.ui.RecycleViewClickListener
import kotlinx.android.synthetic.main.search_pagination_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SearchPaginationFragment : Fragment(), KodeinAware, RecycleViewClickListener {

    override val kodein by kodein()
    private val factory : SearchPaginationViewModelFactory by instance()


    private lateinit var viewModel: SearchPaginationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_pagination_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = GiphyImagesPagedListAdapter(this)
        recycle_view_images.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.setHasFixedSize(true)
            it.adapter = adapter
        }
        viewModel = ViewModelProviders.of(this, factory).get(SearchPaginationViewModel::class.java)
        viewModel.getGiphyImages().observe(this, Observer {
            Log.d("DEBUG", "On Data changed")
            adapter.submitList(it)
        })
    }
    override fun onRecyclerViewItemClick(view: View, image: GiphyImage) {
        when(view.id){
            R.id.imageViewFavor ->{
                viewModel.onFavoriteButtonClick(image)
            }
        }

    }

}
