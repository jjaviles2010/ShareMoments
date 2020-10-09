package com.jlapps.sharemoments.repository

import androidx.lifecycle.LiveData
import com.jlapps.sharemoments.model.Photo

interface PhotoRepository {
    suspend fun insertPhoto(photo: Photo) : Long

    fun getPhotos() : LiveData<List<Photo>>

    suspend fun updatePhoto(photoId: Int, title: String)
}