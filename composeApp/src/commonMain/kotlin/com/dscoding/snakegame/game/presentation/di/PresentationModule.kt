package com.dscoding.snakegame.game.presentation.di

import com.dscoding.snakegame.core.presentation.util.ScopedStoreRegistryViewModel
import com.dscoding.snakegame.game.domain.GameCoordinator
import com.dscoding.snakegame.game.presentation.GameViewModel
import com.dscoding.snakegame.game.presentation.settings.SettingsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformPresentationModule: Module
val presentationModule = module {
    includes(platformPresentationModule)
    viewModelOf(::GameViewModel)
    factoryOf(::GameCoordinator)
    viewModelOf(::ScopedStoreRegistryViewModel)
    viewModelOf(::SettingsViewModel)
}