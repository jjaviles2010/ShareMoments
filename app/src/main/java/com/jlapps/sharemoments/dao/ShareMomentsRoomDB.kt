package com.jlapps.sharemoments.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jlapps.sharemoments.model.Photo

@Database(entities = arrayOf(Photo::class), version = 1)
abstract class ShareMomentsRoomDB : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var INSTANCE: ShareMomentsRoomDB? = null

        fun getDatabase(context: Context): ShareMomentsRoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShareMomentsRoomDB::class.java,
                    "share_moments_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}