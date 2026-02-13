package com.dscoding.snakegame.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.dscoding.snakegame.core.domain.GamePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreGamePreferences(
    private val dataStore: DataStore<Preferences>
): GamePreferences {

    private val highscoreKey = intPreferencesKey("KEY_HIGHSCORE")

    override fun observeHighscore(): Flow<Int> {
        return dataStore.data.map { preferences ->
             preferences[highscoreKey] ?: 0
        }
    }

    override suspend fun setHighscore(value: Int) {
        dataStore.edit { prefs ->
            prefs[highscoreKey] = value
        }
    }
}