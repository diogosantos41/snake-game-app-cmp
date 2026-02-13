package com.dscoding.snakegame.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dscoding.snakegame.data.datastore.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformAppModule = module {
    single<DataStore<Preferences>> {
        createDataStore(androidContext())
    }
}