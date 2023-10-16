package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.FileOpen
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kappdev.txteditor.presentation.editor.ToolbarAction

@Composable
fun Toolbar(
    onAction: (ToolbarAction) -> Unit,
) {
    val color = MaterialTheme.colorScheme.surface
    val shadowColor = MaterialTheme.colorScheme.onSurface

    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = Color.Transparent,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .drawBehind {
                    toolbarForm(
                        background = color,
                        shadowColor = shadowColor.copy(0.24f)
                    )
                },
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
            ToolbarButton(icon = Icons.Rounded.Save) {
                onAction(ToolbarAction.Save)
            }
            ToolbarButton(icon = Icons.Rounded.AddCircleOutline) {
                onAction(ToolbarAction.NewFile)
            }
        }
    }
}

private fun DrawScope.toolbarForm(
    background: Color,
    border: Dp = 8.dp,
    shadowColor: Color
) {
    val width = this.size.width
    val height = this.size.height
    val cornerSize = (height / 3)
    val borderWidth = border.toPx() / width
    val borderHeight = border.toPx() / height
    val cornerBorder = border.toPx() / cornerSize

    val formPath = Path().apply {
        moveTo(0f, height)
        lineTo(0f, cornerSize)
        quadraticBezierTo(
            x1 = 0f,
            y1 = 0f,
            x2 = cornerSize,
            y2 = 0f
        )
        lineTo(width - cornerSize ,0f)
        quadraticBezierTo(
            x1 = width,
            y1 = 0f,
            x2 = width,
            y2 = cornerSize
        )
        lineTo(width, height)
        close()
    }

    drawPath(
        path = formPath,
        color = background
    )
    drawPath(
        path = formPath,
        color = shadowColor
    )

    drawRect(
        brush = Brush.verticalGradient(
            (1f - borderHeight) to background,
            1f to Color.Transparent,
            startY = height,
            endY = 0f
        ),
        topLeft = Offset(cornerSize, 0f),
        size = Size(width - (2 * cornerSize), height)
    )

    drawRect(
        brush = Brush.horizontalGradient(
            0f to Color.Transparent,
            borderWidth to background,
            (1f - borderWidth) to background,
            1f to Color.Transparent,
            startX = 0f,
            endX = width
        ),
        topLeft = Offset(0f, cornerSize),
        size = Size(width, height - cornerSize)
    )

    drawArc(
        brush = Brush.radialGradient(
            (1f - cornerBorder) to background,
            1f to Color.Transparent,
            radius = cornerSize,
            center = Offset(cornerSize, cornerSize)
        ),
        startAngle = -90f,
        sweepAngle = -90f,
        useCenter = true,
        topLeft = Offset.Zero,
        size = Size(cornerSize * 2, cornerSize * 2)
    )

    drawArc(
        brush = Brush.radialGradient(
            (1f - cornerBorder) to background,
            1f to Color.Transparent,
            radius = cornerSize,
            center = Offset(width - cornerSize, cornerSize)
        ),
        startAngle = -90f,
        sweepAngle = 90f,
        useCenter = true,
        topLeft = Offset(width - (cornerSize * 2), 0f),
        size = Size(cornerSize * 2, cornerSize * 2)
    )
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