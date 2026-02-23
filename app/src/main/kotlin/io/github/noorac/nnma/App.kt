package io.github.noorac.nnma

import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path

private val logger = LoggerFactory.getLogger("io.github.noorac.nnma.Application")

fun main() {

    // Keep in mind this is relative path, must be ran from main directory
    Files.createDirectories(Path.of("logs"))
    logger.info("nnma starting up")
    println("Hello World!")
}
