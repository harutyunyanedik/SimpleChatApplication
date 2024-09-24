package com.example.chatapplication.presentation

import android.app.Application
import com.example.chatapplication.data.di.authenticatorModule
import com.example.chatapplication.data.di.dataModule
import com.example.chatapplication.data.di.dataStoreModule
import com.example.chatapplication.data.di.httpClientModule
import com.example.chatapplication.data.di.interceptorModule
import com.example.chatapplication.data.di.repositoryModule
import com.example.chatapplication.presentation.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ChatApplication)
            androidLogger(Level.DEBUG)
            modules(dataStoreModule, interceptorModule, authenticatorModule, httpClientModule, dataModule, repositoryModule, appModule)
        }
    }
}