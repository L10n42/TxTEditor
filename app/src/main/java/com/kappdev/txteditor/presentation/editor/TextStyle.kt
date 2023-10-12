package com.kappdev.txteditor.presentation.editor

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import com.kappdev.txteditor.R

enum class TextStyle(val icon: ImageVector, @StringRes val titleRes: Int, val family: FontFamily) {
    DEFAULT(Icons.Rounded.TextFields, R.string.style_default, FontFamily.Default),
    SERIF(Icons.Rounded.TextFields, R.string.style_serif, FontFamily.Serif),
    MONO(Icons.Rounded.TextFields, R.string.style_mono, FontFamily.Monospace)
}