package com.jlapps.sharemoments.view.photoDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jlapps.sharemoments.R
import com.jlapps.sharemoments.model.Photo
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PhotoDetailsActivity : AppCompatActivity() {

    val photoDetailsViewModel: PhotoDetailsViewModel by viewModel()
    val photo: Photo by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
    }
}