package com.dscoding.snakegame.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.core.domain.models.ControlMode
import com.dscoding.snakegame.core.domain.models.ColorUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DataStoreGamePreferences(
    private val dataStore: DataStore<Preferences>
) : GamePreferences {

    private val highscoreKey = intPreferencesKey("KEY_HIGHSCORE")
    private val soundKey = booleanPreferencesKey("KEY_SOUND")
    private val hapticsKey = booleanPreferencesKey("KEY_HAPTICS")
    private val controlModeKey = stringPreferencesKey("KEY_CONTROL_MODE")
    private val gameColorKey = stringPreferencesKey("KEY_GAME_COLOR")

    override suspend fun setHighscore(value: Int) {
        dataStore.edit { prefs ->
            prefs[highscoreKey] = value
        }
    }

    override fun observeHighscore(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[highscoreKey] ?: 0
        }.distinctUntilChanged()
    }

    override suspend fun setSoundEnabled(isEnabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[soundKey] = isEnabled
        }
    }

    override fun observeSoundEnabled(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[soundKey] ?: false
        }.distinctUntilChanged()
    }

    override suspend fun setHapticsEnabled(isEnabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[hapticsKey] = isEnabled
        }
    }

    override fun observeHapticsEnabled(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[hapticsKey] ?: false
        }.distinctUntilChanged()
    }

    override suspend fun setControlMode(controlMode: ControlMode) {
        dataStore.edit { prefs ->
            prefs[controlModeKey] = controlMode.name
        }
    }

    override fun observeControlMode(): Flow<ControlMode> {
        return dataStore.data.map { preferences ->
            preferences[controlModeKey]?.let {
                ControlMode.valueOf(it)
            } ?: ControlMode.SWIPE
        }
            .distinctUntilChanged()
    }

    override suspend fun setGameColor(color: ColorUi) {
        dataStore.edit { prefs ->
            prefs[gameColorKey] = color.name
        }
    }

    override fun observeGameColor(): Flow<ColorUi> {
        return dataStore.data.map { preferences ->
            preferences[gameColorKey]?.let {
                ColorUi.valueOf(it)
            } ?: ColorUi.BURNT_ORANGE
        }
            .distinctUntilChanged()
    }
}