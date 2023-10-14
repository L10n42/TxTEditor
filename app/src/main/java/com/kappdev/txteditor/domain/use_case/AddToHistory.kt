package com.kappdev.txteditor.domain.use_case

import android.net.Uri
import com.kappdev.txteditor.domain.repository.HistoryRepository
import javax.inject.Inject

class AddToHistory @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(uri: Uri) {
        historyRepository.addToHistory(uri)
    }
}