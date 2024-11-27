package com.kappdev.txteditor.editor_feature.presentation.editor

import androidx.annotation.StringRes
import com.kappdev.txteditor.R

sealed class Dialog(
    @StringRes val titleRes: Int? = null,
    @StringRes val messageRes: Int? = null,
    @StringRes val positiveButtonRes: Int = R.string.btn_save,
    @StringRes val negativeButtonRes: Int = R.string.btn_cancel,
) {
    data object SaveAndNew: Dialog(R.string.title_file_save, R.string.msg_save_changes, negativeButtonRes = R.string.btn_discard)
    data object SaveAndOpen: Dialog(R.string.title_file_save, R.string.msg_save_changes_before_open, negativeButtonRes = R.string.btn_discard)
}
