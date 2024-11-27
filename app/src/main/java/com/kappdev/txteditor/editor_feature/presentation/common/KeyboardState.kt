package com.kappdev.txteditor.editor_feature.presentation.common

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import kotlinx.coroutines.CoroutineScope

enum class Keyboard {
    Opened, Closed
}

@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}

@Composable
fun KeyboardLaunchedEffect(
    block: suspend CoroutineScope.(state: Keyboard) -> Unit
) {
    val keyboardState by keyboardAsState()

    LaunchedEffect(keyboardState) {
        block(keyboardState)
    }
}

@Composable
fun KeyboardClosedEffect(
    block: suspend CoroutineScope.() -> Unit
) {
    val keyboardState by keyboardAsState()

    LaunchedEffect(keyboardState) {
        if (keyboardState == Keyboard.Closed) {
            block()
        }
    }
}

@Composable
fun KeyboardOpenedEffect(
    block: suspend CoroutineScope.() -> Unit
) {
    val keyboardState by keyboardAsState()

    LaunchedEffect(keyboardState) {
        if (keyboardState == Keyboard.Opened) {
            block()
        }
    }
}