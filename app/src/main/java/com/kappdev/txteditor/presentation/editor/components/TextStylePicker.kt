package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.txteditor.presentation.editor.TextStyle

@Composable
fun Settings.TextStylePicker(
    currentStyle: TextStyle,
    border: Boolean = true,
    onStyleChange: (newStyle: TextStyle) -> Unit
) {
    Form(
        hasBorder = border,
        height = 52.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextStyle.values().forEach { style ->
                StyleButton(
                    style = style,
                    isSelected = (currentStyle == style),
                    onSelect = onStyleChange
                )
            }
        }
    }
}

@Composable
private fun StyleButton(
    style: TextStyle,
    isSelected: Boolean,
    onSelect: (TextStyle) -> Unit
) {
    IconButton(
        onClick = { onSelect(style) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = style.icon,
                contentDescription = null,
                tint = when {
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onBackground
                },
            )
            Text(
                text = stringResource(style.titleRes),
                fontSize = 12.sp,
                color = when {
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}