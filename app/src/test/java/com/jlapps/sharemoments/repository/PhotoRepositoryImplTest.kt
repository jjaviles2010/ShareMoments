package com.jlapps.sharemoments.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jlapps.sharemoments.dao.PhotoDao
import com.jlapps.sharemoments.getOrAwaitValue
import com.jlapps.sharemoments.model.Photo
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.*
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

class PhotoRepositoryImplTest {
    private val photo1 = Photo(1, "photo1", "photo_1", "/photo_1.jpg", "JPEG",
        350, 450, nextLong(), 4.0f, 3072)
    private val photo2 = Photo(2, "photo2", "photo_2", "/photo_2.jpg", "JPEG",
        450, 550, nextLong(), 3.0f, 3072)
    private val photo3 = Photo(3, "photo3", "photo_3", "/photo_3.jpg", "JPEG",
        650, 750, nextLong(), 5.0f, 3072)
    private val photos = mutableListOf(photo1, photo2, photo3)
    private lateinit var photoDao: PhotoDao

    // Class under test
    private lateinit var photoRepository: PhotoRepositoryImpl

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createRepository() {
        photoDao = FakeDataSource(photos)
        photoRepository = PhotoRepositoryImpl(photoDao)
    }

    @Test
    fun getPhotos_called_returnsAllPhotos() {
        // When photos are requested from the photos repository
        val allPhotos = photoRepository.getPhotos().getOrAwaitValue()

        // Then all photos are loaded from data source
        assertThat(allPhotos, IsEqual(photos.toList()))
    }

    @Test
    fun insertPhoto_1NewPhoto_photoInserted() = runBlockingTest {
        val idPhoto = nextInt()
        val newPhoto = Photo(idPhoto, "photo4", "photo_4", "/photo_4.jpg", "JPEG",
            850, 550, nextLong(), 4.0f, 3072)

        // When new photo is created
        photoRepository.insertPhoto(newPhoto)

        // Then the new photo exists in dataSource
        assertThat(photos, hasItem(newPhoto))
    }

    @Test
    fun updatePhoto_newTitleForAPhoto_TitleUpdated() = runBlockingTest {
        // When title updated
        val newTitle = "title_new"
        photoRepository.updatePhoto(1, newTitle)
        val titlePhoto1 = photos.first().title

        // Then the photo's title is updated
        assertEquals(newTitle, titlePhoto1)
    }
}


