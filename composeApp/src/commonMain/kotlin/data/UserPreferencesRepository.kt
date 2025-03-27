package data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import model.NotificationSettings
import model.login.AuthData
import model.schedule.SemesterDates
import model.settings.SettingsState
import model.settings.Theme
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

    val semesterDates: Flow<SemesterDates> = dataStore.data.map { preferences ->
        SemesterDates(
            startDate = preferences[SEMESTER_START_DATE] ?: "",
            sessionStartDate = preferences[SESSION_START_DATE] ?: "",
            sessionEndDate = preferences[SESSION_END_DATE] ?: ""
        )
    }

    val isSubjectsGroupingEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_SUBJECTS_GROUPING_ENABLED] ?: false
    }

    val notificationSettings: Flow<NotificationSettings> = dataStore.data.map { preferences ->
        NotificationSettings(
            isSubjectNotificationEnabled = preferences[IS_SUBJECT_NOTIFICATION_ENABLED] ?: false,
            isNewsNotificationsEnabled = preferences[IS_NEWS_NOTIFICATION_ENABLED] ?: false
        )
    }

    val settings: Flow<SettingsState> = dataStore.data.map { preferences ->
        SettingsState(
            theme = preferences[SETTINGS_THEME]?.let { Theme.valueOf(it) } ?: Theme.System,
            softenDarkTheme = preferences[SETTINGS_SOFTEN_DARK_THEME] ?: false,
            pinkMode = preferences[SETTINGS_PINK_MODE] ?: false,
            coloredBorders = preferences[SETTINGS_ENABLE_COLORED_BORDERS] ?: false
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

    suspend fun setSemesterDates(semesterDates: SemesterDates) {
        dataStore.edit { preferences ->
            semesterDates.run {
                preferences[SEMESTER_START_DATE] = startDate
                preferences[SESSION_START_DATE] = sessionStartDate ?: ""
                preferences[SESSION_END_DATE] = sessionEndDate ?: ""
            }
        }
    }

    suspend fun toggleSubjectsGrouping() {
        dataStore.edit { preferences ->
            preferences[IS_SUBJECTS_GROUPING_ENABLED] = !(preferences[IS_SUBJECTS_GROUPING_ENABLED] ?: false)
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun enableSubjectNotification(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_SUBJECT_NOTIFICATION_ENABLED] = enable
        }
    }
    suspend fun enableNewsNotifications(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_NEWS_NOTIFICATION_ENABLED] = enable
        }
    }

    suspend fun setTheme(theme: Theme) {
        dataStore.edit { preferences ->
            preferences[SETTINGS_THEME] = theme.name
        }
    }

    suspend fun setSoftenDarkTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[SETTINGS_SOFTEN_DARK_THEME] = enabled
        }
    }

    suspend fun setEnableColoredBorders(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[SETTINGS_ENABLE_COLORED_BORDERS] = enabled
        }
    }

    suspend fun setPinkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[SETTINGS_PINK_MODE] = enabled
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
        private val SEMESTER_START_DATE = stringPreferencesKey("SEMESTER_START_DATE")
        private val SESSION_START_DATE = stringPreferencesKey("SESSION_START_DATE")
        private val SESSION_END_DATE = stringPreferencesKey("SESSION_END_DATE")
        private val IS_SUBJECTS_GROUPING_ENABLED = booleanPreferencesKey("IS_SUBJECTS_GROUPING_ENABLED")
        private val IS_SUBJECT_NOTIFICATION_ENABLED = booleanPreferencesKey("IS_SUBJECT_NOTIFICATION_ENABLED")
        private val IS_NEWS_NOTIFICATION_ENABLED = booleanPreferencesKey("IS_NEWS_NOTIFICATION_ENABLED")

        private val SETTINGS_THEME = stringPreferencesKey("SETTINGS_THEME")
        private val SETTINGS_SOFTEN_DARK_THEME = booleanPreferencesKey("SETTINGS_SOFTEN_DARK_MODE")
        private val SETTINGS_PINK_MODE = booleanPreferencesKey("SETTINGS_PINK_MODE")
        private val SETTINGS_ENABLE_COLORED_BORDERS = booleanPreferencesKey("SETTINGS_ENABLE_COLORED_BORDERS")
    }
}
