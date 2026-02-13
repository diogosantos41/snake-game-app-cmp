package com.dscoding.snakegame.di

import com.dscoding.snakegame.core.data.datastore.DataStoreGamePreferences
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.game.data.engine.GameEngineImpl
import com.dscoding.snakegame.game.domain.GameEngine
import com.dscoding.snakegame.game.presentation.GameViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformAppModule: Module

val appModule = module {
    includes(platformAppModule)
    viewModelOf(::GameViewModel)
    singleOf(::GameEngineImpl) bind GameEngine::class
    singleOf(::DataStoreGamePreferences) bind GamePreferences::class
}