package com.dscoding.snakegame.game.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dscoding.snakegame.data.audio.AndroidGameAudio
import com.dscoding.snakegame.data.datastore.createDataStore
import com.dscoding.snakegame.data.haptics.AndroidGameHaptics
import com.dscoding.snakegame.game.domain.audio.GameAudio
import com.dscoding.snakegame.game.domain.haptics.GameHaptics
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformDataModule = module {
    single<DataStore<Preferences>> {
        createDataStore(androidContext())
    }
    single<GameAudio> {
        AndroidGameAudio(androidContext(), get(), get())
    }
    single<GameHaptics> {
        AndroidGameHaptics(androidContext(), get(), get())
    }
}