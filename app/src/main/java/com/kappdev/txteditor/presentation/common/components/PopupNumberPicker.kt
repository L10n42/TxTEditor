package com.kappdev.txteditor.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun PopupNumberPicker(
    visible: Boolean,
    range: Iterable<Int>,
    initialValue: Int,
    modifier: Modifier = Modifier,
    offset: IntOffset = IntOffset(0, 0),
    alignment: Alignment = Alignment.Center,
    onDismiss: (value: Int) -> Unit,
) {
    val (selectedValue, selectValue) = remember { mutableIntStateOf(initialValue) }
    val expandedState = remember { MutableTransitionState(false) }
    expandedState.targetState = visible

    LaunchedEffect(initialValue) { selectValue(initialValue) }

    if (expandedState.currentState || expandedState.targetState || !expandedState.isIdle) {
        Popup(
            onDismissRequest = {
                onDismiss(selectedValue)
            },
            alignment = alignment,
            offset = offset,
            properties = PopupProperties(focusable = true)
        ) {
            AnimatedVisibility(
                visibleState = expandedState,
                enter = fadeIn() + scaleIn(
                    initialScale = 0.3f,
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                ),
                exit = scaleOut(
                    targetScale = 0.3f,
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                ) + fadeOut()
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    elevation = 8.dp,
                    modifier = modifier,
                ) {
                    NumberPicker(
                        range = range,
                        value = selectedValue,
                        dividersColor = MaterialTheme.colorScheme.primary,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier.width(65.dp),
                        onValueChange = selectValue
                    )
                }
            }
        }
    }
}