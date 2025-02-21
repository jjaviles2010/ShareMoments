package com.jlapps.sharemoments.di

import androidx.room.Room
import com.jlapps.sharemoments.dao.ShareMomentsRoomDB
import com.jlapps.sharemoments.model.Photo
import com.jlapps.sharemoments.repository.PhotoRepository
import com.jlapps.sharemoments.repository.PhotoRepositoryImpl
import com.jlapps.sharemoments.view.photoDetails.PhotoDetailsViewModel
import com.jlapps.sharemoments.view.photos.PhotosListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PhotosListViewModel(get()) }
    viewModel { PhotoDetailsViewModel(get()) }
}

val uiModule = module {
    factory { Photo() }
}

val repositoryModule = module {
    single<PhotoRepository> { PhotoRepositoryImpl(get()) }
}

val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            ShareMomentsRoomDB::class.java,
            "share_moments_database"
        ).build()
    }

    single {
        get<ShareMomentsRoomDB>().photoDao()
    }
}