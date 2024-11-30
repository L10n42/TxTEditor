package com.kappdev.txteditor.editor_feature.presentation.common.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object DialogComponents {

    @Composable
    fun Text(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = modifier,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    @Composable
    fun Title(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            modifier = modifier,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}