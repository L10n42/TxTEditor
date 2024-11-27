package com.kappdev.txteditor.editor_feature.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getHistory(): Flow<List<Uri>>

    suspend fun clearHistory()

    suspend fun removeFromHistory(file: Uri)

    suspend fun addToHistory(file: Uri)
}