package com.jlapps.sharemoments.view.photos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jlapps.sharemoments.R
import com.jlapps.sharemoments.view.photoDetails.PhotoDetailsActivity
import kotlinx.android.synthetic.main.activity_photos_list.*
import kotlinx.android.synthetic.main.include_loading.*
import kotlinx.android.synthetic.main.photo_list_item.*
import kotlinx.android.synthetic.main.photo_list_item.view.*
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
        photosListViewModel.isLoading.observe(this, Observer {
            if (it == true) {
                containerLoading.visibility = View.VISIBLE
            } else {
                containerLoading.visibility = View.GONE
            }
        })

        photosListViewModel.operationSuccess.observe(this, Observer {
            if (it == false) {
                Snackbar.make(listViewCoordinator, getString(R.string.lbl_error_loading), Snackbar.LENGTH_LONG).show()
            }
        })

        photosListViewModel.photos.observe(this, Observer {

            rvPhotosList.adapter = PhotosListAdapter(
                it
            ) { photo ->
                val intentDetails = Intent(this, PhotoDetailsActivity::class.java)
                intentDetails.putExtra("PHOTO", photo)
                startActivity(intentDetails)
            }

            if(it.isNotEmpty()) {
                lbNoDataFound.visibility = View.GONE
            }

            rvPhotosList.layoutManager = LinearLayoutManager(this)

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.share_moments_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.ac_add_photo -> showPhotoDetailsScreen()
        }
        return true
    }

    private fun showPhotoDetailsScreen() {
        val intent = Intent(this, PhotoDetailsActivity::class.java)
        startActivity(intent)
    }
}