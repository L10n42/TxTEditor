package com.kappdev.txteditor.editor_feature.presentation.editor

import android.net.Uri
import androidx.annotation.StringRes
import com.kappdev.txteditor.R

sealed class Dialog(
    @StringRes val titleRes: Int,
    @StringRes val messageRes: Int,
    @StringRes val positiveButtonRes: Int = R.string.btn_save,
    @StringRes val negativeButtonRes: Int = R.string.btn_cancel,
) {
    data object SaveAndNew: Dialog(R.string.unsaved_changes, R.string.msg_save_changes, negativeButtonRes = R.string.btn_discard)
    data object SaveAndOpen: Dialog(R.string.unsaved_changes, R.string.msg_save_changes_before_open, negativeButtonRes = R.string.btn_discard)
    data class SaveAndOpenFromHistory(val fileUri: Uri): Dialog(R.string.unsaved_changes, R.string.msg_save_changes_before_open, negativeButtonRes = R.string.btn_discard)
}
