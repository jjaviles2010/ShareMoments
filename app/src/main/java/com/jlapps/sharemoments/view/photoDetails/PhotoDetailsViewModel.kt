package com.jlapps.sharemoments.view.photoDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlapps.sharemoments.model.Photo
import com.jlapps.sharemoments.repository.PhotoRepository
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(val photoRepository: PhotoRepository) : ViewModel(){

    lateinit var photo: LiveData<Photo>

    fun insertPhoto(photo: Photo) {
        viewModelScope.launch {
            photoRepository.insertPhoto(photo)
        }
    }

    fun updatePhoto(photo: Photo) {
        viewModelScope.launch {
            photoRepository.updatePhoto(photo)
        }
    }


}