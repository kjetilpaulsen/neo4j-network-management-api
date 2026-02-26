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

    @Test
    fun `downloads file when missing`() {
        val dataDir = tmp.resolve("data")

        val downloader = Downloader {
            DownloadResponse(200, ByteArrayInputStream("payload".toByteArray()))
        }

        val out = DataDump.ensurePresent(
            dataDir = dataDir,
            dumpId = "x.dump",
            dumpUrl = URI.create("http://example.invalid/x.dump"),
            downloader = downloader,
        )
        assertEquals(dataDir.resolve("x.dump"), out)
        assertEquals("payload", Files.readString(out))
    }

    @Test
    fun `non-2xx throws and cleans part file`() {
        val dataDir = tmp.resolve("data")

        val downloader = Downloader {
            DownloadResponse(404, ByteArrayInputStream(ByteArray(0)))
        }
        assertThrows(IllegalStateException::class.java) {
            DataDump.ensurePresent(
                dataDir = dataDir,
                dumpId = "x.dump",
                dumpUrl = URI.create("http://example.invalid/x.dump"),
                downloader = downloader,
            )
        }

        val part = dataDir.resolve("x.dump.part")
        assertFalse(Files.exists(part))
        assertFalse(Files.exists(dataDir.resolve("x.dump")))
    }
}
