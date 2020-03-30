package com.minhbka.giphyimagesearchexample.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhbka.giphyimagesearchexample.R
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.ui.*
import com.minhbka.giphyimagesearchexample.utils.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FavoriteFragment : Fragment(), RecycleViewClickListener, KodeinAware {

    override val kodein by kodein()
    private val factory : FavoriteViewModelFactory by instance()
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(FavoriteViewModel::class.java)

        viewModel.getFavorImage()
        val adapter = GiphyImagesAdapter(this)
        recycle_view_images.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.setHasFixedSize(true)
            it.adapter = adapter
        }

        viewModel.favorImages.observeOnce(this, Observer {images->
            Log.d("DEBUG", "On data change")
            images?.let {
                adapter.submitList(images.toMutableList())
            }
        })

    }

    override fun onRecyclerViewItemClick(view: View, image: GiphyImage) {
        when(view.id){
            R.id.imageViewFavor ->{
                view.isSelected = !view.isSelected
                viewModel.onFavoriteButtonClick(image)
            }
        }

    }


}
