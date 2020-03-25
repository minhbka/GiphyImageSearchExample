package com.minhbka.giphyimagesearchexample.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhbka.giphyimagesearchexample.R
import com.minhbka.giphyimagesearchexample.network.GiphyApi
import com.minhbka.giphyimagesearchexample.network.NetworkConnectionInterceptor
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import com.minhbka.giphyimagesearchexample.utils.hide
import com.minhbka.giphyimagesearchexample.utils.show
import com.minhbka.giphyimagesearchexample.utils.snackbar
import com.minhbka.giphyimagesearchexample.utils.toast
import kotlinx.android.synthetic.main.fragment_search.*

import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment(), GiphyListener, KodeinAware {

    override val kodein by kodein()
    private val factory : GiphyViewModelFactory by instance()

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

        viewModel = ViewModelProviders.of(this, factory).get(GiphyViewModel::class.java)
        viewModel.giphyListener = this
        viewModel.getSearchImage()
        viewModel.getFavorImage()
        viewModel.images.observe(viewLifecycleOwner, Observer {images->
            recycle_view_images.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = GiphyImagesAdapter(images)
            }

        })
    }

    override fun onStarted() {
        progress_bar.show()
        activity!!.toast("Start Loading")
    }

    override fun onSuccess() {
        progress_bar.hide()
        activity!!.toast("Success Loading")

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)

    }

}
