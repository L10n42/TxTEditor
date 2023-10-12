package com.kappdev.txteditor.domain.use_case

import android.app.Application
import android.net.Uri
import com.kappdev.txteditor.domain.util.Result
import com.kappdev.txteditor.domain.util.fail
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class ReadFile @Inject constructor(
    private val app: Application
) {

    operator fun invoke(fileUri: Uri?): Result<String> {
        fileUri ?: return Result.fail("Unknown file path.")

        return try {
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
            stringBuilder.append('\n') // Add line breaks if needed
        }
        return stringBuilder.toString()
    }
}