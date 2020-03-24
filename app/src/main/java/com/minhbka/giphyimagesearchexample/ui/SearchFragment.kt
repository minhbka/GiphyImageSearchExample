package com.minhbka.giphyimagesearchexample.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.minhbka.giphyimagesearchexample.R
import com.minhbka.giphyimagesearchexample.network.GiphyApi
import com.minhbka.giphyimagesearchexample.network.NetworkConnectionInterceptor
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment() {

    private lateinit var factory: GiphyViewModelFactory
    private lateinit var viewModel: GiphyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = GiphyApi(networkConnectionInterceptor)
        val repository = GiphyRepository(api)
        factory = GiphyViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(GiphyViewModel::class.java)
        viewModel.getSearchImage()
        viewModel.images.observe(viewLifecycleOwner, Observer {images->
            recycle_view_images.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = GiphyImagesAdapter(images)
            }

        })
    }


}
