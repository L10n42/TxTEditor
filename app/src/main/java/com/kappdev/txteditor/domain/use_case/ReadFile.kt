package com.kappdev.txteditor.domain.use_case

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.kappdev.txteditor.domain.util.Result
import com.kappdev.txteditor.domain.util.fail
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

private const val DOCUMENT_ACCESS_FLAGS = (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

class ReadFile @Inject constructor(
    private val app: Application
) {
    operator fun invoke(fileUri: Uri?): Result<String> {
        fileUri ?: return Result.fail("Unknown file path.")

        return try {
            app.contentResolver.takePersistableUriPermission(fileUri, DOCUMENT_ACCESS_FLAGS)
            val inputStream = app.contentResolver.openInputStream(fileUri)
            val text = readContentFrom(inputStream)
            inputStream?.close()

            Result.Success(text)
        } catch (e: FileNotFoundException) {
            Result.fail("File not found.")
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    private fun readContentFrom(inputStream: InputStream?): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
            stringBuilder.append('\n')
        }
        return stringBuilder.toString()
    }
}