package io.github.noorac.nnma

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.ByteArrayInputStream
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path

class DataDumpTest {
    @TempDir
    lateinit var tmp: Path

    @Test
    fun `returns existing file without downloading`() {
        val dataDir = tmp.resolve("data")
        Files.createDirectory(dataDir)

        val target = dataDir.resolve("x.dump")
        Files.writeString(target, "already-here")

        var called = false
        val downloader = Downloader {
            called = true
            DownloadResponse(200, ByteArrayInputStream("new".toByteArray()))
        }

        val out = DataDump.ensurePresent(
            dataDir = dataDir,
            dumpId = "x.dump",
            dumpUrl = URI.create("http://example.invalid/x.dump"),
            downloader = downloader,
        )

        assertEquals(target, out)
        assertFalse(called)
        assertEquals("already-here", Files.readString(target))
    }
}
