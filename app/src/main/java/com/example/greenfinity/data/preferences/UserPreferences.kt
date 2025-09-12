package com.example.greenfinity.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Data class untuk status sesi
data class LoginSession(
    val isLoggedIn: Boolean
)

class UserPreferences(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login_session")


    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")


    suspend fun saveLoginSession() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
        }
    }


    fun getLoginSession(): Flow<LoginSession> {
        return context.dataStore.data.map { preferences ->
            LoginSession(
                isLoggedIn = preferences[IS_LOGGED_IN] ?: false
            )
        }
    }


    suspend fun clearLoginSession() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = false
        }
    }
}