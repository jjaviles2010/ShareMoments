package com.jlapps.sharemoments.di

import androidx.room.Room
import com.jlapps.sharemoments.dao.ShareMomentsRoomDB
import com.jlapps.sharemoments.model.Photo
import com.jlapps.sharemoments.repository.PhotoRepository
import com.jlapps.sharemoments.repository.PhotoRepositoryImpl
import org.koin.dsl.module

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