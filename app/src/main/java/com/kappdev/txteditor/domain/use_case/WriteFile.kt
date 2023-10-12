package com.kappdev.txteditor.domain.use_case

import android.app.Application
import android.net.Uri
import com.kappdev.txteditor.domain.util.Result
import com.kappdev.txteditor.domain.util.fail
import java.io.FileNotFoundException
import java.io.OutputStreamWriter
import javax.inject.Inject

class WriteFile @Inject constructor(
    private val app: Application
) {

    operator fun invoke(path: Uri?, content: String): Result<String> {
        path ?: return Result.fail("Unknown file path.")

        return try {
            val outputStream = app.contentResolver.openOutputStream(path)
            val writer = OutputStreamWriter(outputStream)
            writer.write(content)
            writer.close()

            Result.Success(content)
        } catch (e: FileNotFoundException) {
            Result.fail("File not found.")
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}