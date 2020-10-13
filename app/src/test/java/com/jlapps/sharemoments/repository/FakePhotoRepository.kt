package com.jlapps.sharemoments.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlapps.sharemoments.model.Photo
import kotlin.random.Random.Default.nextLong

class FakePhotoRepository(var photos: MutableList<Photo> = mutableListOf()) : PhotoRepository {

    private val observablePhotos = MutableLiveData<List<Photo>>()

    override suspend fun insertPhoto(photo: Photo): Long {
        photos.add(photo)
        return nextLong()
    }

    override fun getPhotos(): LiveData<List<Photo>> {
        observablePhotos.value = photos
        return observablePhotos
    }

    override suspend fun updatePhoto(photoId: Int, title: String) {
        photos?.map {
            if (it.idPhoto == photoId)
                it.title = title
        }
    }

}