package com.jlapps.sharemoments.view.photos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jlapps.sharemoments.getOrAwaitValue
import com.jlapps.sharemoments.model.Photo
import com.jlapps.sharemoments.repository.FakePhotoRepository
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class PhotosListViewModelTest {
    lateinit var photos: MutableList<Photo>
    // Use a fake repository to be injected into the viewmodel
    private lateinit var photoRepository: FakePhotoRepository

    // Subject under test
    private lateinit var photoListViewModel: PhotosListViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        val photo1 = Photo(1, "photo1", "photo_1", "/photo_1.jpg", "JPEG",
            350, 450, Random.nextLong(), 4.0f, 3072)
        val photo2 = Photo(2, "photo2", "photo_2", "/photo_2.jpg", "JPEG",
            450, 550, Random.nextLong(), 3.0f, 3072)
        val photo3 = Photo(3, "photo3", "photo_3", "/photo_3.jpg", "JPEG",
            650, 750, Random.nextLong(), 5.0f, 3072)
        photos = mutableListOf(photo1, photo2, photo3)

        photoRepository = FakePhotoRepository(photos)

        photoListViewModel = PhotosListViewModel(photoRepository)
    }

    @Test
    fun getPhotos_returnsAllPhotos() {
        //When called get photos
        photoListViewModel.getPhotos()

        // Then all photos are returned
        val allPhotos = photoListViewModel.photos.getOrAwaitValue()

        assertThat(allPhotos, IsEqual(photos.toList()))
    }
}