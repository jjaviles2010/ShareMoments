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
    val message: MutableLiveData<String> = MutableLiveData()
    lateinit var photo: LiveData<Photo>

    fun insertPhoto(photo: Photo) {
        isLoading.value = true
        try {
            viewModelScope.launch {
                photoRepository.insertPhoto(photo)
            }
            message.value = "Photo saved successfully"
        } catch (error: Error) {
            message.value = "Error saving photo"
        } finally {
            isLoading.value = false
        }
    }

    fun updatePhoto(photo: Photo) {
        isLoading.value = true
        try {
            viewModelScope.launch {
                photoRepository.updatePhoto(photo)
            }
            message.value = "Photo updated successfully"
        } catch (error: Error) {
            message.value = "Error updating photo"
        } finally {
            isLoading.value = false
        }
    }

}