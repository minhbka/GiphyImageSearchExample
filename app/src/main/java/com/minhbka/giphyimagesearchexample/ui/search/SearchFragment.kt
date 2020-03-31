package com.minhbka.giphyimagesearchexample.ui.search


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.minhbka.giphyimagesearchexample.databinding.FragmentSearchBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhbka.giphyimagesearchexample.R
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage
import com.minhbka.giphyimagesearchexample.ui.*
import com.minhbka.giphyimagesearchexample.utils.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 *
 */
@ExperimentalCoroutinesApi
@FlowPreview
class SearchFragment : Fragment(), RecycleViewClickListener, KodeinAware {

    override val kodein by kodein()
    private val factory : SearchViewModelFactory by instance()
    private lateinit var adapter: GiphyImagesPagedListAdapter
    private lateinit var viewModel: SearchViewModel
    private lateinit var last_search_keyword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DEBUG", "On onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container,false)
        if (!::viewModel.isInitialized) {

            viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
            binding.searchviewmodel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("DEBUG", "On onActivityCreated")
        adapter = GiphyImagesPagedListAdapter(this)
        recycle_view_images.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.setHasFixedSize(true)
            it.adapter = adapter
        }
        if (savedInstanceState != null){
            last_search_keyword = savedInstanceState.getString(LAST_SEARCH_KEYWORD)!!
            viewModel.queryChannel.offer(last_search_keyword)
        }

        viewModel.getGiphyImagesLiveData().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }

        })

        viewModel.getProgressLoadStatus().observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("DEBUG", "On Loading Status changed")
                handleLoadingStatus(it)
            }

        })
        viewModel.getKeyword().observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("DEBUG", "keyword: $it")
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

    private fun handleLoadingStatus(loadingStatus: LoadingStatus){
        when (loadingStatus.statusCode) {
            LOADING -> {
                progress_bar.show()
                activity!!.toast(loadingStatus.message)
            }
            SUCCESS -> {
                progress_bar.hide()
                activity!!.toast(loadingStatus.message)
            }
            FAILURE -> {
                progress_bar.hide()
                root_layout.snackbar(loadingStatus.message)
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (::adapter.isInitialized){
            Log.d("DEBUG", "On SaveInstanceState")
            outState.putString(LAST_SEARCH_KEYWORD, viewModel.queryChannel.valueOrNull.orEmpty())
        }
    }

}
