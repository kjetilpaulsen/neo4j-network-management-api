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

    }
}

