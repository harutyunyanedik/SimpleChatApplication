package com.example.mangochatapplication.presentation

import android.app.Application
import com.example.mangochatapplication.presentation.di.appModule
import com.example.mangochatapplication.presentation.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MangoChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MangoChatApplication)
            androidLogger(Level.DEBUG)
            modules(appModule, authModule)
        }
    }
}