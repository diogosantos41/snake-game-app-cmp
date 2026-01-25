package com.dscoding.snakegame

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform