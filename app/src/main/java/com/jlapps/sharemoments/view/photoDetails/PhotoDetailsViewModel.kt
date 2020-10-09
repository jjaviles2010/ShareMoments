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

    fun insertPhoto(photo: Photo) {
        isLoading.value = true
        try {
            viewModelScope.launch {
                photoRepository.insertPhoto(photo)
            }
            createdSuccess.value = true
        } catch (error: Error) {
            createdSuccess.value = false
        } finally {
            isLoading.value = false
        }
    }

    fun updatePhoto(photo: Photo) {
        isLoading.value = true
        try {
            viewModelScope.launch {
                photoRepository.updatePhoto(photo.idPhoto, photo.title)
            }
            updatedSuccess.value = true
        } catch (error: Error) {
            updatedSuccess.value = false
        } finally {
            isLoading.value = false
        }
    }

}