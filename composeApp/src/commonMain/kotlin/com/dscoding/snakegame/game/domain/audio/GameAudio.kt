package com.dscoding.snakegame.game.domain.audio

import com.dscoding.snakegame.game.domain.audio.models.SoundEffect

interface GameAudio {
    fun playSoundEffect(effect: SoundEffect)
    fun startMusic()
    fun stopMusic()
}