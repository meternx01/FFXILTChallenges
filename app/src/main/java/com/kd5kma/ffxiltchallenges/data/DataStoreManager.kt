package com.kd5kma.ffxiltchallenges.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class DataStoreManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val IS_CATSEYE = booleanPreferencesKey("is_catseye")
    }

    val isCatseye: Flow<Boolean> = dataStore.data.map { preferences ->
            preferences[IS_CATSEYE] ?: false
        }

    suspend fun setCatseye(isCatseye: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_CATSEYE] = isCatseye
        }
    }


}