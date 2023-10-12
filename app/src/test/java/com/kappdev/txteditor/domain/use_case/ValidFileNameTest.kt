package com.kappdev.txteditor.domain.use_case

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidFileNameTest {

    @Test
    fun `file name with valid extensions, should return same file name`() {
        assertEquals("file.txt", ValidFileName("file.txt"))
        assertEquals("document.text", ValidFileName("document.text"))
        assertEquals("data.plain", ValidFileName("data.plain"))
    }

    @Test
    fun `file name with invalid extensions, should replace it with (txt)`() {
        assertEquals("report.txt", ValidFileName("report.doc"))
        assertEquals("notes.txt", ValidFileName("notes.invalid"))
        assertEquals("backupbackup.txt", ValidFileName("backup.backup.bak"))
        assertEquals("image.txt", ValidFileName("image.jpg"))
    }

    @Test
    fun `file name with no extensions, should add (txt)`() {
        assertEquals("music.txt", ValidFileName("music"))
        assertEquals("proposal.txt", ValidFileName("proposal."))
        assertEquals("noextension.txt", ValidFileName("noextension.."))
    }

}