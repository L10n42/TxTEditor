package com.kappdev.txteditor.presentation.editor

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.txteditor.R
import com.kappdev.txteditor.domain.use_case.AddToHistory
import com.kappdev.txteditor.domain.use_case.GetFileName
import com.kappdev.txteditor.domain.use_case.ReadFile
import com.kappdev.txteditor.domain.use_case.ShareText
import com.kappdev.txteditor.domain.use_case.WriteFile
import com.kappdev.txteditor.domain.util.Result
import com.kappdev.txteditor.domain.util.getMessageOrEmpty
import com.kappdev.txteditor.presentation.common.ComponentState
import com.kappdev.txteditor.presentation.common.LoadingState
import com.kappdev.txteditor.presentation.common.SnackbarState
import com.kappdev.txteditor.presentation.common.mutableComponentStateOf
import com.kappdev.txteditor.presentation.common.mutableLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditorViewModel @Inject constructor(
    private val readFile: ReadFile,
    private val writeFile: WriteFile,
    private val shareText: ShareText,
    private val getFilename: GetFileName,
    private val addToHistory: AddToHistory,
    private val app: Application
) : ViewModel() {

    private val _loadingState = mutableLoadingState()
    val loadingState: LoadingState = _loadingState

    private val _dialogState = mutableComponentStateOf<Dialog?>(null)
    val dialogState: ComponentState<Dialog?> = _dialogState

    private val _openFileFlow = MutableSharedFlow<Unit>()
    val openFileFlow: SharedFlow<Unit> = _openFileFlow

    private val _saveFileFlow = MutableSharedFlow<Unit>()
    val saveFileFlow: SharedFlow<Unit> = _saveFileFlow

    private var originalText = ""
    var text = mutableStateOf("")
        private set

    private val fileUri = mutableStateOf<Uri?>(null)

    val snackbarState = SnackbarState(app)

    fun saveAndNew() {
        if (fileUri.value == null) launchFileSave() else saveAndThen { newFile() }
    }

    fun saveAndOpen() {
        if (fileUri.value == null) launchFileSave() else saveAndThen { launchFileOpen() }
    }

    fun saveFile() = saveAndThen { value ->
        originalText = value
        snackbarState.show(R.string.msg_file_saved)
    }

    private fun saveAndThen(block: suspend (value: String) -> Unit) = launchLoading {
        val writeResult = writeFile(fileUri.value, text.value)
        when (writeResult) {
            is Result.Failure -> snackbarState.show(writeResult.getMessageOrEmpty())
            is Result.Success -> block(writeResult.value)
        }
    }

    fun readFileFromUri() = launchLoading {
        val readResult = readFile(fileUri.value)
        when (readResult) {
            is Result.Success -> setContent(readResult.value)
            is Result.Failure -> snackbarState.show(readResult.getMessageOrEmpty())
        }
    }

    private fun launchLoading(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.suspendLoading(block)
        }
    }

    private fun isContentChanged() = (text.value != originalText)

    fun checkChangesAndOpen() {
        if (isContentChanged()) _dialogState.show(Dialog.SaveAndOpen) else launchFileOpen()
    }

    fun checkChangesAndNew() {
        if (isContentChanged()) _dialogState.show(Dialog.SaveAndNew) else newFile()
    }

    fun checkFileAndSave() {
        if (isContentChanged()) {
            if (fileUri.value == null) launchFileSave() else saveFile()
        } else {
            viewModelScope.launch { snackbarState.show(R.string.nothing_to_save_msg) }
        }
    }

    fun newFile() {
        setContent("")
        fileUri.value = null
    }

    private fun setContent(content: String) {
        originalText = content
        text.value = content
    }

    fun copyToClipboard() {
        if (text.value.isEmpty()) {
            viewModelScope.launch { snackbarState.show(R.string.copy_error_msg) }
        } else {
            val clipboardManager = app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", text.value)
            clipboardManager.setPrimaryClip(clip)
            viewModelScope.launch { snackbarState.show(R.string.copied_to_clipboard) }
        }
    }

    fun share() {
        if (text.value.isEmpty()) {
            viewModelScope.launch { snackbarState.show(R.string.share_error_msg) }
        } else {
            shareText(text.value)
        }
    }

    fun getWordCount(): Int {
        val words = text.value.trim().split(Regex("\\s+"))
        return if (text.value.isEmpty()) 0 else words.size
    }

    fun hideDialog() = _dialogState.hide()

    fun launchFileOpen() {
        viewModelScope.launch { _openFileFlow.emit(Unit) }
    }

    private fun launchFileSave() {
        viewModelScope.launch { _saveFileFlow.emit(Unit) }
    }

    fun setFileUri(uri: Uri) {
        this.fileUri.value = uri
        updateHistory()
    }

    private fun updateHistory() {
        fileUri.value?.let { uri ->
            viewModelScope.launch(Dispatchers.IO) {
                addToHistory(uri)
            }
        }
    }

    fun getCurrentFilename(): String {
        return fileUri.value?.let(getFilename::invoke) ?: "unnamed.txt"
    }

    fun setText(value: String) {
        this.text.value = value
    }
}