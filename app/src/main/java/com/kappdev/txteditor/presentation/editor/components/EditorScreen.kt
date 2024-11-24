package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kappdev.txteditor.data.Setting
import com.kappdev.txteditor.data.SettingsManager
import com.kappdev.txteditor.domain.model.EditorSettings
import com.kappdev.txteditor.presentation.common.InfoSnackbarHandler
import com.kappdev.txteditor.presentation.common.components.LoadingDialog
import com.kappdev.txteditor.presentation.common.components.bottomFadingEdge
import com.kappdev.txteditor.presentation.common.components.topFadingEdge
import com.kappdev.txteditor.presentation.editor.EditorViewModel
import com.kappdev.txteditor.presentation.editor.LaunchersHandler
import com.kappdev.txteditor.presentation.editor.ToolbarAction
import kotlinx.coroutines.launch

@Composable
fun EditorScreen(
    settingsManager: SettingsManager,
    viewModel: EditorViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val (isSettingsVisible, showSettings) = remember { mutableStateOf(false) }
    val (isHistoryVisible, showHistory) = remember { mutableStateOf(false) }
    val editorSettings by settingsManager.getEditorSettings().collectAsState(EditorSettings())
    val isThemeDark by settingsManager.getValueAsState(Setting.Theme)

    LoadingDialog(isVisible = viewModel.loadingState.isLoading.value)

    if (isHistoryVisible) {
        HistoryBottomSheet(
            openFile = { fileUri ->
                viewModel.setFileUri(fileUri)
                viewModel.readFileFromUri()
            },
            onDismiss = { showHistory(false) }
        )
    }

    if (isSettingsVisible) {
        SettingsBottomSheet(
            viewModel = viewModel,
            settingsManager = settingsManager,
            onDismiss = { showSettings(false) }
        )
    }

    DialogController(viewModel)
    LaunchersHandler(viewModel)

    InfoSnackbarHandler(hostState = scaffoldState.snackbarHostState, snackbarState = viewModel.snackbarState)

    Scaffold(
        bottomBar = {
            Toolbar(isThemeDark) { action ->
                when (action) {
                    ToolbarAction.OpenSettings -> showSettings(true)
                    ToolbarAction.OpenHistory -> showHistory(true)
                    ToolbarAction.OpenFile -> viewModel.checkChangesAndOpen()
                    ToolbarAction.NewFile -> viewModel.checkChangesAndNew()
                    ToolbarAction.Save -> viewModel.checkFileAndSave()
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState.snackbarHostState
            ) { data ->
                Snackbar(snackbarData = data)
            }
        }
    ) { paddingValues ->
        EditorField(
            value = viewModel.text.value,
            settings = editorSettings,
            onValueChange = viewModel::setText,
            scrollTo = { position ->
                scope.launch { scrollState.animateScrollBy(position) }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                .topFadingEdge(
                    MaterialTheme.colorScheme.background,
                    isVisible = scrollState.canScrollBackward
                )
                .bottomFadingEdge(
                    MaterialTheme.colorScheme.background,
                    isVisible = scrollState.canScrollForward
                )
                .verticalScroll(scrollState)
        )
    }
}