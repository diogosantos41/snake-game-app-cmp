package com.dscoding.snakegame.game.data.di

import com.dscoding.snakegame.core.data.datastore.DataStoreGamePreferences
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.game.data.engine.GameEngineImpl
import com.dscoding.snakegame.game.domain.engine.GameEngine
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformDataModule: Module

val dataModule = module {
    includes(platformDataModule)
    singleOf(::GameEngineImpl) bind GameEngine::class
    singleOf(::DataStoreGamePreferences) bind GamePreferences::class
}