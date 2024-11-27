package com.kappdev.txteditor.editor_feature.data.repository

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kappdev.txteditor.editor_feature.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val HISTORY_DATASTORE = "history_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(HISTORY_DATASTORE)

private val HISTORY = stringPreferencesKey("HISTORY")

class HistoryRepositoryImpl @Inject constructor(
    private val context: Context
) : HistoryRepository {

    private val historyTypeToken = object : TypeToken<List<String>>() {}.type

    private val gson = Gson()

    override fun getHistory(): Flow<List<Uri>> = context.dataStore.data.map { preferences ->
        val historyJson = preferences.getHistoryOrEmpty()
        historyJson.parseHistoryOrEmpty()
    }

    override suspend fun clearHistory() {
        manageHistoryItems { history ->
            history.clear()
        }
    }

    override suspend fun removeFromHistory(file: Uri) {
        manageHistoryItems { history ->
            history.remove(file)
        }
    }

    override suspend fun addToHistory(file: Uri) {
        manageHistoryItems { history ->
            history.remove(file)
            history.add(file)
            if (history.size > 10) {
                history.removeFirst()
            }
        }
    }

    private suspend fun manageHistoryItems(transformation: (MutableList<Uri>) -> Unit) {
        context.dataStore.edit { preferences ->
            val historyJson = preferences.getHistoryOrEmpty()
            val history = historyJson.parseHistoryOrEmpty().toMutableList()

            transformation(history)

            preferences[HISTORY] = history.toJson()
        }
    }

    private fun Preferences.getHistoryOrEmpty(): String = this[HISTORY] ?: ""

    private fun List<Uri>.toJson(): String {
        return gson.toJson(this.map { it.toString() }, historyTypeToken)
    }

    private fun String.parseHistoryOrEmpty(): List<Uri> {
        return gson.fromJson<List<String>>(this, historyTypeToken)?.map { Uri.parse(it) } ?: emptyList()
    }
}