package com.kappdev.txteditor.editor_feature.presentation.common.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.kappdev.txteditor.editor_feature.presentation.common.components.FadePosition.*

private enum class FadePosition {
    LEFT, RIGHT, BOTTOM, TOP
}

fun Modifier.topFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    ratio: Float = DEFAULT_RATIO,
) = edgeShade(
    position = TOP,
    color = color,
    ratio = ratio,
    isVisible = isVisible
)

fun Modifier.bottomFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    ratio: Float = DEFAULT_RATIO,
) = edgeShade(
    position = BOTTOM,
    color = color,
    ratio = ratio,
    isVisible = isVisible
)

fun Modifier.rightFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    ratio: Float = DEFAULT_RATIO,
) = edgeShade(
    position = RIGHT,
    color = color,
    ratio = ratio,
    isVisible = isVisible
)

fun Modifier.leftFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    ratio: Float = DEFAULT_RATIO,
) = edgeShade(
    position = LEFT,
    color = color,
    ratio = ratio,
    isVisible = isVisible
)

private fun Modifier.edgeShade(
    position: FadePosition,
    color: Color,
    ratio: Float,
    isVisible: Boolean,
) = composed {

    val shadeAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        label = "shade alpha"
    )

    drawWithContent {
        this@drawWithContent.drawContent()

        val startOffset = this.size.getFadeStartOffset(position)
        val endOffset = this.size.getFadeEndOffset(position)

        drawRect(
            brush = Brush.linearGradient(
                0f to color,
                ratio to Color.Transparent,
                start = startOffset,
                end = endOffset
            ),
            alpha = shadeAlpha,
            topLeft = Offset.Zero,
            size = this.size
        )
    }
}

private fun Size.getFadeEndOffset(position: FadePosition): Offset {
    return when (position) {
        LEFT -> Offset(this.width, 0f)
        RIGHT -> Offset(0f, 0f)
        BOTTOM -> Offset(0f, 0f)
        TOP -> Offset(0f, this.height)
    }
}

private fun Size.getFadeStartOffset(position: FadePosition): Offset {
    return when (position) {
        LEFT -> Offset(0f, 0f)
        RIGHT -> Offset(this.width, 0f)
        BOTTOM -> Offset(0f, this.height)
        TOP -> Offset(0f, 0f)
    }
}

private const val DEFAULT_RATIO = 0.1f