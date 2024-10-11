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
import model.user.UserInfo

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    val isAuthorized: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[CSRF]?.isNotEmpty() ?: false &&
            preferences[ORIOKS_IDENTITY]?.isNotEmpty() ?: false &&
            preferences[ORIOKS_SESSION]?.isNotEmpty() ?: false
    }

    val authData: Flow<AuthData> = dataStore.data.map { preferences ->
        AuthData(
            csrf = preferences[CSRF] ?: "",
            orioksIdentity = preferences[ORIOKS_IDENTITY] ?: "",
            orioksSession = preferences[ORIOKS_SESSION] ?: ""
        )
    }

    val userInfo: Flow<UserInfo> = dataStore.data.map { preferences ->
        UserInfo(
            name = preferences[USER_NAME] ?: "",
            login = preferences[USER_LOGIN] ?: "",
            group = preferences[USER_GROUP] ?: ""
        )
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

    suspend fun setUserInfo(userInfo: UserInfo) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = userInfo.name
            preferences[USER_LOGIN] = userInfo.login
            preferences[USER_GROUP] = userInfo.group
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
        private val CSRF = stringPreferencesKey("CSRF")
        private val ORIOKS_IDENTITY = stringPreferencesKey("ORIOKS_IDENTITY")
        private val ORIOKS_SESSION = stringPreferencesKey("ORIOKS_SESSION")
        private val IS_SESSION_INVALIDATED = booleanPreferencesKey("IS_SESSION_INVALIDATED")
        private val USER_NAME = stringPreferencesKey("USER_NAME")
        private val USER_LOGIN = stringPreferencesKey("USER_LOGIN")
        private val USER_GROUP = stringPreferencesKey("USER_GROUP")
    }
}
