package com.dscoding.snakegame.di

import com.dscoding.snakegame.game.data.engine.GameEngineImpl
import com.dscoding.snakegame.game.domain.GameEngine
import com.dscoding.snakegame.game.presentation.GameViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::GameViewModel)
    singleOf(::GameEngineImpl) bind GameEngine::class
}