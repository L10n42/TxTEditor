package com.kappdev.txteditor.editor_feature.presentation.editor

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun LaunchersHandler(
    viewModel: EditorViewModel
) {
    val fileOpenTrigger = viewModel.openFileFlow
    val fileSaveTrigger = viewModel.saveFileFlow

    val openFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let { viewModel.openFile(it) }
        }
    )

    val saveFileLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("text/plain"),
        onResult = { uri ->
            uri?.let {
                viewModel.setFileUri(uri)
                viewModel.saveFile()
            }
        }
    )

    LaunchedEffect(fileOpenTrigger) {
        fileOpenTrigger.collectLatest {
            openFileLauncher.launch(arrayOf("text/plain"))
        }
    }

    LaunchedEffect(fileSaveTrigger) {
        fileSaveTrigger.collectLatest {
            saveFileLauncher.launch(getDefaultFileName())
        }
    }
}

private fun getDefaultFileName(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.getDefault())
    val currentTime = Date()
    return "${dateFormat.format(currentTime)}.txt"
}