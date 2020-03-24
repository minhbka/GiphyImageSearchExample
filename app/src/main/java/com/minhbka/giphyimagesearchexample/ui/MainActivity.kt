package com.minhbka.giphyimagesearchexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.minhbka.giphyimagesearchexample.R
import com.minhbka.giphyimagesearchexample.network.GiphyApi
import com.minhbka.giphyimagesearchexample.repository.GiphyRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = GiphyRepository(GiphyApi())
        GlobalScope.launch(Dispatchers.Main) {
            val giphyImage = repository.getGiphySearchImage()
            Log.d("GiphyResponse", giphyImage.toString())
        }
    }
}
