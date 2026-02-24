package io.github.noorac.nnma

import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path

fun main() {

    // Bootstrap Logging
    Logging.init()
    val logger = LoggerFactory.getLogger("io.github.noorac.nnma.Application")
    logger.info("Logging bootstrap completed")

    // Ensure we have the data

    // Run rest of program

    println("Hello World!")
}
