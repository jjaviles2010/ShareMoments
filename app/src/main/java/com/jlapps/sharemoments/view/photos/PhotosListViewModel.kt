package com.jlapps.sharemoments.view.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlapps.sharemoments.model.Photo
import com.jlapps.sharemoments.repository.PhotoRepository

class PhotosListViewModel(val photoRepository: PhotoRepository) : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()

    lateinit var photos: LiveData<List<Photo>>

    fun getPhotos() {
        isLoading.value = true
        try {
            photos = photoRepository.getPhotos()
        } catch (error: Error) {
            message.value = "Error loading photos"
        } finally {
            isLoading.value = false
        }
    }
}