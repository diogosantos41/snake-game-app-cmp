package com.dscoding.snakegame.data.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import com.dscoding.snakegame.R
import com.dscoding.snakegame.game.domain.audio.GameAudio
import com.dscoding.snakegame.game.domain.audio.models.SoundEffect

class AndroidGameAudio(
    context: Context
) : GameAudio {

    private val appContext = context.applicationContext

    private val soundPool: SoundPool
    private val sfxMap: Map<SoundEffect, Int>
    private val loaded = mutableSetOf<Int>()

    init {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(attrs)
            .build()

        soundPool.setOnLoadCompleteListener { _, soundId, status ->
            if (status == 0) loaded.add(soundId)
        }

        sfxMap = mapOf(
            SoundEffect.EAT to soundPool.load(appContext, R.raw.food, 1),
            SoundEffect.GAME_OVER to soundPool.load(appContext, R.raw.fail, 1),
        )
    }

    private var musicPlayer: MediaPlayer? = null

    override fun playSoundEffect(effect: SoundEffect) {
        val id = sfxMap[effect] ?: return
        if (id !in loaded) {
            Log.d("AndroidGameAudio", "SFX not loaded yet: $effect")
            return
        }
        soundPool.play(id, 1f, 1f, 0, 0, 1f)
    }

    override fun startMusic() {
        if (musicPlayer != null) return

        musicPlayer = MediaPlayer.create(appContext, R.raw.music)?.apply {
            isLooping = true
            setOnErrorListener { _, what, extra ->
                Log.e("AndroidGameAudio", "Music error what=$what extra=$extra")
                true
            }
            start()
        }
    }

    override fun stopMusic() {
        musicPlayer?.apply {
            try {
                stop()
            } catch (_: IllegalStateException) {

            }
            release()
        }
        musicPlayer = null
    }
}