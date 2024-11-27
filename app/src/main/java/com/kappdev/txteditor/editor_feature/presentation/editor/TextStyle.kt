package com.kappdev.txteditor.editor_feature.presentation.editor

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.text.font.FontFamily
import com.kappdev.txteditor.R

enum class TextStyle(@DrawableRes val iconRes: Int, @StringRes val titleRes: Int, val family: FontFamily) {
    DEFAULT(R.drawable.font_style_default, R.string.style_default, FontFamily.Default),
    SERIF(R.drawable.font_style_serif, R.string.style_serif, FontFamily.Serif),
    MONO(R.drawable.font_style_mono, R.string.style_mono, FontFamily.Monospace)
}