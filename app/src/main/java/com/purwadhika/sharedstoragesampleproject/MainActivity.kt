package com.purwadhika.sharedstoragesampleproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.purwadhika.sharedstoragesampleproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: GaleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupList()
        viewModel.imagesLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.loadImages()
    }

    private fun setupList(){
        adapter = GaleryAdapter()
        binding.listThumbnail.also { view ->
            view.layoutManager = GridLayoutManager(this, 3)
            view.adapter = adapter
        }
    }
}