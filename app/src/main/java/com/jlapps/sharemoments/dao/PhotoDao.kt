package com.jlapps.sharemoments.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jlapps.sharemoments.model.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: Photo) : Long

    @Query("SELECT * FROM photo")
    fun getPhotos() : LiveData<List<Photo>>

    @Query("UPDATE photo SET title=:title WHERE idPhoto=:photoId")
    suspend fun updatePhoto(photoId: Int, title: String)
}