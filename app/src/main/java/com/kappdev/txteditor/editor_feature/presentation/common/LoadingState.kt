package com.kappdev.txteditor.editor_feature.presentation.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

sealed interface LoadingState {
    val isLoading: State<Boolean>
}

sealed interface MutableLoadingState : LoadingState {
    override val isLoading: State<Boolean>
    fun loading(block: () -> Unit)
    suspend fun suspendLoading(block: suspend () -> Unit)
}

private class MutableLoadingStateHolder : MutableLoadingState {

    private var _isLoading = mutableStateOf(false)

    override val isLoading: State<Boolean>
        get() = _isLoading

    override fun loading(block: () -> Unit) {
        _isLoading.value = true
        block()
        _isLoading.value = false
    }

    override suspend fun suspendLoading(block: suspend () -> Unit) {
        _isLoading.value = true
        block()
        _isLoading.value = false
    }
}

fun mutableLoadingState(): MutableLoadingState {
    return MutableLoadingStateHolder()
}