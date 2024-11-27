package com.kappdev.txteditor.editor_feature.domain.use_case

import android.content.Context
import android.content.Intent
import com.kappdev.txteditor.R
import javax.inject.Inject

class ShareText @Inject constructor(
    private val context: Context
) {

    operator fun invoke(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)

        val sheetTitle = context.getString(R.string.share_via)
        val chooserIntent = Intent.createChooser(intent, sheetTitle)
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }
}