package com.kappdev.txteditor.editor_feature.domain.use_case

import android.net.Uri
import com.kappdev.txteditor.editor_feature.domain.repository.HistoryRepository
import javax.inject.Inject

class AddToHistory @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(uri: Uri) {
        historyRepository.addToHistory(uri)
    }
}