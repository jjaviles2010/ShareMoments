package com.jlapps.sharemoments.repository

import androidx.lifecycle.LiveData
import com.jlapps.sharemoments.dao.PhotoDao
import com.jlapps.sharemoments.model.Photo

class PhotoRepositoryImpl(val photoDao: PhotoDao) : PhotoRepository {
    override suspend fun insertPhoto(photo: Photo): Long {
        return photoDao.insert(photo)
    }

    override fun getPhotos(): LiveData<List<Photo>> {
        return photoDao.getPhotos()
    }

    override suspend fun updatePhoto(photo: Photo) {
        photoDao.updatePhoto(photo)
    }
}