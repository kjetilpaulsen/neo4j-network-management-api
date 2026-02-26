package io.github.noorac.nnma

import org.slf4j.LoggerFactory
import java.io.InputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

fun interface Downloader {
    fun open(uri: URI) : DownloadResponse
}

data class DownloadResponse (
    val statusCode: Int,
    val body: InputStream,
)

object HttpDownloader: Downloader {
    private val client = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build()

    override fun open(uri: URI): DownloadResponse {
        val request = HttpRequest.newBuilder(uri).GET().build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofInputStream())
        return DownloadResponse(response.statusCode(), response.body())
    }
}

object DataDump {
    private val logger = LoggerFactory.getLogger(javaClass)

    private fun defaultDumpUrl(dumpId: String): URI=
        URI.create("https://raw.githubusercontent.com/neo4j-graph-examples/network-management/main/data/$dumpId")

    /**
     * Ensures the dump exists in [dataDir]. Downloads if missing.
     */

    fun ensurePresent(
        dataDir: Path = Xdg.dataDir,
        dumpId: String = AppInfo.DUMP_ID,
        dumpUrl: URI = defaultDumpUrl(dumpId),
        downloader: Downloader = HttpDownloader,
    ): Path {
        Files.createDirectories(dataDir)

        val target = dataDir.resolve(dumpId)

        // Check if exists and return if it does
        if (Files.exists(target)) {
            logger.info("Dump target already exists: {}", target)
            return target
        }

        // If it doesn't exist download it
        logger.warn("Dump missing, downloading from {}", dumpUrl)

        val tmp = dataDir.resolve("$dumpId.part")
        try {
            val resp = downloader.open(dumpUrl)
            if (resp.statusCode !in 200..299) {
                throw IllegalStateException("Download failed: HTTP ${resp.statusCode} from $dumpUrl")
            }
            
            resp.body.use { input ->
                Files.copy(input, tmp, StandardCopyOption.REPLACE_EXISTING)
            }

            Files.move(
                tmp,
                target,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE
            )

            logger.info("Dump downloaded to {}", target)
            return target
        } catch (e: Exception) {
            runCatching { Files.deleteIfExists(tmp) }
            logger.error("Failed to download dump", e)
            throw e
        }
    }
}

