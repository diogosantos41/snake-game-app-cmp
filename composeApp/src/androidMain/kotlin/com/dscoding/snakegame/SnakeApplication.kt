package com.dscoding.snakegame

import android.app.Application
import com.dscoding.snakegame.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class SnakeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SnakeApplication)
            androidLogger()
        }
    }
}