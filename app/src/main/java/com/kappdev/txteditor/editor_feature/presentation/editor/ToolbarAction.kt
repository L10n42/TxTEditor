package com.kappdev.txteditor.editor_feature.presentation.editor

sealed interface ToolbarAction {
    data object OpenSettings : ToolbarAction
    data object OpenHistory : ToolbarAction
    data object OpenFile : ToolbarAction
    data object Save : ToolbarAction
    data object NewFile : ToolbarAction
}