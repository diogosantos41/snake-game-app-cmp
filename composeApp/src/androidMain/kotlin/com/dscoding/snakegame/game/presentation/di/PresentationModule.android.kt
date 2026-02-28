package com.dscoding.snakegame.game.presentation.di

import com.dscoding.snakegame.game.presentation.utils.PlatformShareSheet
import org.koin.dsl.module

actual val platformPresentationModule = module {
    factory { PlatformShareSheet(get()) }
}