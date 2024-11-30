package com.kappdev.txteditor.editor_feature.domain.use_case

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.kappdev.txteditor.editor_feature.domain.util.Result
import com.kappdev.txteditor.editor_feature.domain.util.fail
import java.io.FileNotFoundException
import javax.inject.Inject

private const val DOCUMENT_ACCESS_FLAGS = (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

class ReadFile @Inject constructor(
    private val app: Application
) {
    operator fun invoke(fileUri: Uri?): Result<String> {
        fileUri ?: return Result.fail("Unknown file path.")

        return try {
            app.contentResolver.takePersistableUriPermission(fileUri, DOCUMENT_ACCESS_FLAGS)

            val text = app.contentResolver.openInputStream(fileUri)?.bufferedReader().use { it?.readText() }

            if (text != null) Result.Success(text) else Result.fail("Can't read the file.")
        } catch (e: FileNotFoundException) {
            Result.fail("File not found.")
        } catch (e: SecurityException) {
            Result.fail("Permission denied. You need to allow access.")
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}