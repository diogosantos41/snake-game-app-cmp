package com.dscoding.snakegame.game.presentation.di

import com.dscoding.snakegame.game.presentation.utils.PlatformShareSheet
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual val platformPresentationModule = module {
    factoryOf(::PlatformShareSheet)
}
