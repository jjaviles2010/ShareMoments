package com.jlapps.sharemoments.view.photoDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jlapps.sharemoments.getOrAwaitValue
import com.jlapps.sharemoments.model.Photo
import com.jlapps.sharemoments.repository.FakePhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

@ExperimentalCoroutinesApi
class PhotoDetailsViewModelTest {
    lateinit var photos: MutableList<Photo>
    // Use a fake repository to be injected into the viewmodel
    private lateinit var photoRepository: FakePhotoRepository

    // Subject under test
    private lateinit var photoDetailsViewModel: PhotoDetailsViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setupViewModel() {
        Dispatchers.setMain(testDispatcher)

        val photo1 = Photo(1, "photo1", "photo_1", "/photo_1.jpg", "JPEG",
            350, 450, Random.nextLong(), 4.0f, 3072)
        val photo2 = Photo(2, "photo2", "photo_2", "/photo_2.jpg", "JPEG",
            450, 550, Random.nextLong(), 3.0f, 3072)
        val photo3 = Photo(3, "photo3", "photo_3", "/photo_3.jpg", "JPEG",
            650, 750, Random.nextLong(), 5.0f, 3072)
        photos = mutableListOf(photo1, photo2, photo3)

        photoRepository = FakePhotoRepository(photos)

        photoDetailsViewModel = PhotoDetailsViewModel(photoRepository)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun insertPhoto_newPhotoSaved() {
        //When adding new photo
        val idPhoto = Random.nextInt()
        val newPhoto = Photo(idPhoto, "photo4", "photo_4", "/photo_4.jpg", "JPEG",
            850, 550, Random.nextLong(), 4.0f, 3072)

        photoDetailsViewModel.insertPhoto(newPhoto)

        //Then the new photo is saved
        val result = photoDetailsViewModel.createdSuccess.getOrAwaitValue()

        assert(result)
    }

    @Test
    fun updatePhoto_withNewTitle_TitleUpdated() {
        // When title updated
        val newTitle = "title_new"
        val photoToUpdate = photos.first()
        photoToUpdate.title = newTitle
        photoDetailsViewModel.updatePhoto(photoToUpdate)

        // Then the photo's title is updated
        var result = photoDetailsViewModel.updatedSuccess.getOrAwaitValue()
        assert(result)
    }

}