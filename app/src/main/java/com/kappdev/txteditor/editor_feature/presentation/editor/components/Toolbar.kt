package com.kappdev.txteditor.editor_feature.presentation.editor.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.FileOpen
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kappdev.txteditor.core.presentation.common.components.dropShadow
import com.kappdev.txteditor.editor_feature.presentation.editor.ToolbarAction

@Composable
fun Toolbar(
    isContentChanged: Boolean,
    onAction: (ToolbarAction) -> Unit,
) {
    Surface(
        shape = ToolbarShape,
        modifier = Modifier.dropShadow(
            shape = ToolbarShape,
            color = Color.Black.copy(0.15f),
            offsetY = (-1).dp,
            blur = 8.dp
        )
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
            ToolbarButton(icon = Icons.Rounded.History) {
                onAction(ToolbarAction.OpenHistory)
            }
            ToolbarButton(icon = Icons.Rounded.FileOpen) {
                onAction(ToolbarAction.OpenFile)
            }
            ToolbarButton(
                icon = Icons.Rounded.Save,
                isBadgeVisible = isContentChanged
            ) {
                onAction(ToolbarAction.Save)
            }
            ToolbarButton(icon = Icons.Rounded.AddCircleOutline) {
                onAction(ToolbarAction.NewFile)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolbarButton(
    icon: ImageVector,
    isBadgeVisible: Boolean = false,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Box {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )

            AnimatedVisibility(
                visible = isBadgeVisible,
                enter = fadeIn() + scaleIn(),
                exit = scaleOut() + fadeOut(),
                modifier = Modifier.align(Alignment.TopEnd).offset(2.dp, (-2).dp)
            ) {
                Badge(containerColor = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

private val ToolbarShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)