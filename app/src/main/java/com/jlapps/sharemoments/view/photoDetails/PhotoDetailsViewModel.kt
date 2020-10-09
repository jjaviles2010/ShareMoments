package com.jlapps.sharemoments.view.photoDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlapps.sharemoments.model.Photo
import com.jlapps.sharemoments.repository.PhotoRepository
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(val photoRepository: PhotoRepository) : ViewModel(){

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val createdSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val updatedSuccess: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var photo: LiveData<Photo>

    fun insertPhoto(photo: Photo) = viewModelScope.launch{
        try {
            isLoading.value = true
            photoRepository.insertPhoto(photo)
            createdSuccess.value = true
        } catch (error: Error) {
            isLoading.value = false
            createdSuccess.value = false
        }
    }

    fun updatePhoto(photo: Photo) = viewModelScope.launch {
        try {
            isLoading.value = true
            photoRepository.updatePhoto(photo.idPhoto, photo.title)
            updatedSuccess.value = true
        } catch (error: Error) {
            isLoading.value = false
            updatedSuccess.value = false
        }
    }

}