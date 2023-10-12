package com.kappdev.txteditor.domain.model

import com.kappdev.txteditor.data.SettingsDefaults
import com.kappdev.txteditor.presentation.editor.TextStyle

data class EditorSettings(
    val textSize: Int = SettingsDefaults.TEXT_SIZE,
    val textStyle: TextStyle = SettingsDefaults.TEXT_STYLE
)
