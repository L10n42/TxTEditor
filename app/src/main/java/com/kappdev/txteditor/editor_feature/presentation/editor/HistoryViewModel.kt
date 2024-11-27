package com.kappdev.txteditor.editor_feature.presentation.editor

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.txteditor.editor_feature.domain.repository.HistoryRepository
import com.kappdev.txteditor.editor_feature.domain.use_case.GetFileName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val getFilename: GetFileName
) : ViewModel() {

    var history = mutableStateOf<List<Uri>>(emptyList())
        private set

    fun getHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.getHistory().collectLatest { newHistory ->
                history.value = newHistory
            }
        }
    }

    fun getFilenameOf(uri: Uri): String {
        return getFilename(uri) ?: "Unrecognizable"
    }

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.clearHistory()
        }
    }

    fun removeFromHistory(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.removeFromHistory(uri)
        }
    }
}