package com.kappdev.txteditor.editor_feature.presentation.editor

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LaunchersHandler(
    viewModel: EditorViewModel
) {
    val fileOpenTrigger = viewModel.openFileFlow
    val fileSaveTrigger = viewModel.saveFileFlow

    val openFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                viewModel.setFileUri(uri)
                viewModel.readFileFromUri()
            }
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
            saveFileLauncher.launch("unnamed.txt")
        }
    }
}