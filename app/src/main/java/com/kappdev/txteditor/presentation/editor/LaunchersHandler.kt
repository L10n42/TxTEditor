package com.kappdev.txteditor.presentation.editor

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

@Composable
fun LaunchersHandler(
    viewModel: EditorViewModel
) {
    val fileOpenState = viewModel.openFileFlow.collectAsState(initial = null)
    val fileSaveState = viewModel.saveFileFlow.collectAsState(initial = null)

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

    LaunchedEffect(fileOpenState.value) {
        fileOpenState.value?.let {
            openFileLauncher.launch(arrayOf("text/plain"))
        }
    }

    LaunchedEffect(fileSaveState.value) {
        fileSaveState.value?.let {
            saveFileLauncher.launch("unnamed.txt")
        }
    }
}