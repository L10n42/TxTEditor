package com.kappdev.txteditor.data

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kappdev.txteditor.domain.model.EditorSettings
import com.kappdev.txteditor.presentation.editor.TextStyle
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val SETTINGS_DATASTORE = "settings_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SETTINGS_DATASTORE)

sealed class Setting<T>(val key: Preferences.Key<T>, val default: T) {
    data object Theme: Setting<Boolean>(SettingsKey.THEME, SettingsDefaults.THEME)
    data object TextSize: Setting<Int>(SettingsKey.TEXT_SIZE, SettingsDefaults.TEXT_SIZE)
    data object TextStyle: Setting<String>(SettingsKey.TEXT_STYLE, SettingsDefaults.TEXT_STYLE.name)
}

object SettingsDefaults {
    const val THEME = true
    const val TEXT_SIZE = 16
    val TEXT_STYLE = TextStyle.DEFAULT
}

object SettingsKey {
    val THEME = booleanPreferencesKey("IS_THEME_DARK")
    val TEXT_SIZE = intPreferencesKey("TEXT_SIZE")
    val TEXT_STYLE = stringPreferencesKey("TEXT_STYLE")
}

class SettingsManager @Inject constructor(
    private val context: Context
) {

    fun getEditorSettings() = context.dataStore.data.map { preferences ->
        EditorSettings(
            textSize = preferences.get(Setting.TextSize),
            textStyle = TextStyle.valueOf(preferences.get(Setting.TextStyle))
        )
    }

    @Composable
    fun <T> getValueAsState(setting: Setting<T>): State<T> {
        return getValueBy(setting).collectAsState(setting.default)
    }

    private fun <T> Preferences.get(setting: Setting<T>): T {
        return this[setting.key] ?: setting.default
    }

    private fun <T> getValueBy(setting: Setting<T>) = context.dataStore.data.map { preferences ->
        preferences[setting.key] ?: setting.default
    }

    suspend fun <T> setValueTo(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}