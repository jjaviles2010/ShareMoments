package com.jlapps.sharemoments.view.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jlapps.sharemoments.model.Photo
import com.jlapps.sharemoments.repository.PhotoRepository

class PhotosListViewModel(val photoRepository: PhotoRepository) : ViewModel() {

    lateinit var photos: LiveData<List<Photo>>

    fun getPhotos() {
        photos = photoRepository.getPhotos()
    }
}