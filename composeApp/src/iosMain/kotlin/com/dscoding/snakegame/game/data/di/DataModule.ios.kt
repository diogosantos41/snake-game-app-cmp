package com.dscoding.snakegame.game.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dscoding.snakegame.data.audio.IosGameAudio
import com.dscoding.snakegame.data.datastore.createDataStore
import com.dscoding.snakegame.data.haptics.IosGameHaptics
import com.dscoding.snakegame.game.domain.audio.GameAudio
import com.dscoding.snakegame.game.domain.haptics.GameHaptics
import org.koin.dsl.module

actual val platformDataModule = module {
    single<DataStore<Preferences>> {
        createDataStore()
    }
    single<GameAudio> {
        IosGameAudio(get(), get())
    }
    single<GameHaptics> {
        IosGameHaptics(get(), get())
    }
}