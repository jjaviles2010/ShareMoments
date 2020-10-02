package com.jlapps.sharemoments

import android.app.Application
import com.jlapps.sharemoments.di.dbModule
import com.jlapps.sharemoments.di.repositoryModule
import com.jlapps.sharemoments.di.uiModule
import com.jlapps.sharemoments.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    viewModelModule,
                    uiModule,
                    repositoryModule,
                    dbModule
                )
            )
        }
    }
}