package com.kappdev.txteditor.domain.use_case

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import javax.inject.Inject

class GetFileName @Inject constructor(
    private val context: Context
) {

    operator fun invoke(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            result = getFilenameOf(uri)
        }
        if (result == null) {
            result = parseFilenameOfPath(uri.path)
        }
        return result
    }

    private fun parseFilenameOfPath(path: String?): String? {
        val cut = path?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            return path?.substring(cut + 1)
        }
        return null
    }

    private fun getFilenameOf(uri: Uri): String? {
        try {
            val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    return cursor.getString(nameIndex)
                }
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }
}