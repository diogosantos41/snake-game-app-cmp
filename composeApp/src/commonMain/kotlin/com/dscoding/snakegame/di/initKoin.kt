package com.dscoding.snakegame.di

import com.dscoding.snakegame.game.data.di.dataModule
import com.dscoding.snakegame.game.presentation.di.presentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            appModule,
            presentationModule,
            dataModule
        )
    }
}