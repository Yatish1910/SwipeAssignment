package com.example.swipeassignment.di

import android.app.Application
import com.example.swipeassignment.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        //For starting koin
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}