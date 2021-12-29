package com.thahira.example.jokesmvvmapp

import android.app.Application
import com.thahira.example.jokesmvvmapp.di.appModule
import com.thahira.example.jokesmvvmapp.di.networkModule
import com.thahira.example.jokesmvvmapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class JokeApp : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@JokeApp)
            modules(listOf(networkModule, appModule, viewModelModule))
        }
    }
}