package com.dscoding.snakegame.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dscoding.snakegame.data.audio.IosGameAudio
import com.dscoding.snakegame.data.datastore.createDataStore
import com.dscoding.snakegame.game.domain.audio.GameAudio
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformAppModule = module {
    single<DataStore<Preferences>> {
        createDataStore()
    }
    singleOf(::IosGameAudio) bind GameAudio::class
}