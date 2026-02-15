package com.dscoding.snakegame.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dscoding.snakegame.data.audio.AndroidGameAudio
import com.dscoding.snakegame.data.datastore.createDataStore
import com.dscoding.snakegame.data.haptics.AndroidGameHaptics
import com.dscoding.snakegame.game.domain.audio.GameAudio
import com.dscoding.snakegame.game.domain.haptics.GameHaptics
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformAppModule = module {
    single<DataStore<Preferences>> {
        createDataStore(androidContext())
    }
    single<GameAudio> {
        AndroidGameAudio(androidContext())
    }
    single<GameHaptics> {
        AndroidGameHaptics(androidContext())
    }
}