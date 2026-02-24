package io.github.noorac.nnma

import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

object DataDump {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val dumpUrl: URI = URI.create("https://raw.githubusercontent.com/neo4j-graph-examples/network-management/main/data/".plus(AppInfo.DUMP_ID))

    fun ensurePresent(): Path {
        Files.createDirectories(Xdg.dataDir)

        val target: Path = Xdg.dataDir.resolve(AppInfo.DUMP_ID)
        if (Files.exists(target)) {
            logger.info("Dump target already exists: {}", target)
            return target
        }
        logger.warn("Dump missing, downloading from {}", dumpUrl)
        val tmp: Path = Files.createTempFile(Xdg.dataDir, dumpFileName, ".part")

        try {
            val client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build()

            val request = HttpRequest.newBuilder(dumpUrl)
                .GET()
                .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofInputStream())

            if (response.statusCode() !in 200..299) {
                throw IlleagalStateException("Download failed: HTTP ${response.statusCode()} from $dumpUrl")
            }

        }
    }
}

