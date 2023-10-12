package com.kappdev.txteditor.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

sealed interface ComponentState<T> {
    val data: State<T>
    val isVisible: State<Boolean>
}

sealed interface MutableComponentState<T> : ComponentState<T> {
    override val data: State<T>
    override val isVisible: State<Boolean>
    fun show(data: T)
    fun show()
    fun hide()
}

private class MutableComponentStateHolder<T>(initialData: T) : MutableComponentState<T> {

    private var _data = mutableStateOf(initialData)

    override val data: State<T>
        get() = _data

    private var _isVisible = mutableStateOf(false)

    override val isVisible: State<Boolean>
        get() = _isVisible


    override fun show() {
        _isVisible.value = true
    }

    override fun show(data: T) {
        _data.value = data
        _isVisible.value = true
    }

    override fun hide() {
        _isVisible.value = false
    }
}

fun <T> mutableComponentStateOf(initialData: T): MutableComponentState<T> {
    return MutableComponentStateHolder(initialData)
}

@Composable
fun <T> rememberMutableComponentState(initialData: T) = remember {
    mutableComponentStateOf(initialData)
}