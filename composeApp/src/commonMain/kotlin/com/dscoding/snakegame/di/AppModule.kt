package com.dscoding.snakegame.di

import com.dscoding.snakegame.app.MainViewModel
import com.dscoding.snakegame.core.data.datastore.DataStoreGamePreferences
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.core.presentation.util.ScopedStoreRegistryViewModel
import com.dscoding.snakegame.game.data.engine.GameEngineImpl
import com.dscoding.snakegame.game.domain.GameCoordinator
import com.dscoding.snakegame.game.domain.engine.GameEngine
import com.dscoding.snakegame.game.presentation.GameViewModel
import com.dscoding.snakegame.game.presentation.settings.SettingsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformAppModule: Module

val appModule = module {
    includes(platformAppModule)

    viewModelOf(::MainViewModel)

    viewModelOf(::GameViewModel)
    factoryOf(::GameCoordinator)

    viewModelOf(::ScopedStoreRegistryViewModel)
    viewModelOf(::SettingsViewModel)

    singleOf(::GameEngineImpl) bind GameEngine::class
    singleOf(::DataStoreGamePreferences) bind GamePreferences::class
}