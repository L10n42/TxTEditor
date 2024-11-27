package com.kappdev.txteditor.editor_feature.domain.model

import com.kappdev.txteditor.editor_feature.data.SettingsDefaults
import com.kappdev.txteditor.editor_feature.presentation.editor.TextStyle

data class EditorSettings(
    val textSize: Int = SettingsDefaults.TEXT_SIZE,
    val textStyle: TextStyle = SettingsDefaults.TEXT_STYLE,
    val isBold: Boolean = SettingsDefaults.IS_TEXT_BOLD,
    val isItalic: Boolean = SettingsDefaults.IS_TEXT_ITALIC,
    val showLineNumbering: Boolean = SettingsDefaults.SHOW_LINE_NUMBERING
)
