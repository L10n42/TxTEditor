package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.FileOpen
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kappdev.txteditor.presentation.editor.ToolbarAction
import com.kappdev.txteditor.ui.theme.AppTheme

@Composable
fun Toolbar(
    onAction: (ToolbarAction) -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ToolbarButton(icon = Icons.Rounded.Settings) {
                onAction(ToolbarAction.OpenSettings)
            }
            ToolbarButton(icon = Icons.Rounded.FileOpen) {
                onAction(ToolbarAction.OpenFile)
            }
            ToolbarButton(icon = Icons.Rounded.Save) {
                onAction(ToolbarAction.Save)
            }
            ToolbarButton(icon = Icons.Rounded.AddCircleOutline) {
                onAction(ToolbarAction.NewFile)
            }
        }
    }
}

@Composable
private fun ToolbarButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null
        )
    }
}