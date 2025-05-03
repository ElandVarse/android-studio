package com.example.kotlincounter

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val CLICK_COUNT = intPreferencesKey("click_count")
    }

    val clickCountFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[CLICK_COUNT] ?: 0
        }

    suspend fun saveClickCount(count: Int) {
        context.dataStore.edit { preferences ->
            preferences[CLICK_COUNT] = count
        }
    }
}
