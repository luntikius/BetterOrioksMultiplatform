package data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import model.login.AuthData

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    val isAuthorized: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[CSRF]?.isNotEmpty() ?: false &&
            preferences[ORIOKS_IDENTITY]?.isNotEmpty() ?: false &&
            preferences[ORIOKS_SESSION]?.isNotEmpty() ?: false
    }

    suspend fun isSessionInvalidated(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[IS_SESSION_INVALIDATED] ?: false
        }.first()
    }

    suspend fun setAuthData(authData: AuthData) {
        dataStore.edit { preferences ->
            preferences[CSRF] = authData.csrf
            preferences[ORIOKS_IDENTITY] = authData.orioksIdentity
            preferences[ORIOKS_SESSION] = authData.orioksSession
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[CSRF] = ""
            preferences[ORIOKS_IDENTITY] = ""
            preferences[ORIOKS_SESSION] = ""
        }
    }

    private companion object {
        private val CSRF = stringPreferencesKey("csrf")
        private val ORIOKS_IDENTITY = stringPreferencesKey("ORIOKS_IDENTITY")
        private val ORIOKS_SESSION = stringPreferencesKey("ORIOKS_SESSION")
        private val IS_SESSION_INVALIDATED = booleanPreferencesKey("IS_SESSION_INVALIDATED")
    }
}
