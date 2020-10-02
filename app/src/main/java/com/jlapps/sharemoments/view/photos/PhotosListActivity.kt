package com.jlapps.sharemoments.view.photos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jlapps.sharemoments.R
import kotlinx.android.synthetic.main.activity_photos_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class PhotosListActivity : AppCompatActivity() {

    private val photosListViewModel: PhotosListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos_list)

        photosListViewModel.getPhotos()

        setupObservers()
    }

    private fun setupObservers() {
        photosListViewModel.photos.observe(this, Observer {
            rvPhotosList.adapter = PhotosListAdapter(
                it
            ) { photo ->
                //TODO : Implement navigation to DetailActivity
                Toast.makeText(this, "Photo ${photo.title} selected!",Toast.LENGTH_SHORT).show()
            }

            rvPhotosList.layoutManager = LinearLayoutManager(this)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.share_moments_menu,menu)
        return true
    }

}