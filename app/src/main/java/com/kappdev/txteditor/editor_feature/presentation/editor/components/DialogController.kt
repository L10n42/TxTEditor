package com.kappdev.txteditor.editor_feature.presentation.editor.components

import androidx.compose.runtime.Composable
import com.kappdev.txteditor.editor_feature.presentation.common.components.InfoDialog
import com.kappdev.txteditor.editor_feature.presentation.editor.Dialog
import com.kappdev.txteditor.editor_feature.presentation.editor.EditorViewModel

@Composable
fun DialogController(
    viewModel: EditorViewModel
) {
    val isDialogVisible = viewModel.dialogState.isVisible.value
    val dialogData = viewModel.dialogState.data.value
    
    if (isDialogVisible) {
        when (dialogData) {
            Dialog.SaveAndNew -> {
                InfoDialog(
                    data = dialogData,
                    onDismiss = viewModel::hideDialog,
                    onNegative = viewModel::newFile,
                    onPositive = viewModel::saveAndNew
                )
            }
            Dialog.SaveAndOpen -> {
                InfoDialog(
                    data = dialogData,
                    onDismiss = viewModel::hideDialog,
                    onNegative = viewModel::launchFileOpen,
                    onPositive = viewModel::saveAndOpen
                )
            }
            is Dialog.SaveAndOpenFromHistory -> {
                InfoDialog(
                    data = dialogData,
                    onDismiss = viewModel::hideDialog,
                    onNegative = { viewModel.openHistoryFile(dialogData.fileUri) },
                    onPositive = { viewModel.saveAndOpenFromHistory(dialogData.fileUri) }
                )
            }
            is Dialog.SaveAndOpenSharedText -> {
                InfoDialog(
                    data = dialogData,
                    onDismiss = viewModel::hideDialog,
                    onNegative = { viewModel.newFileWithText(dialogData.text) },
                    onPositive = { viewModel.saveAndOpenSharedText(dialogData.text) }
                )
            }
            null -> TODO()
        }
    }
}

