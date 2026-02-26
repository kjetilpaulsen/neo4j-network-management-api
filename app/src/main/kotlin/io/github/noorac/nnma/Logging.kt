package io.github.noorac.nnma

import java.nio.file.Files

object Logging {
    /**
     * Must be called before any LoggerFactory.getLogger(..) is evalueted
     */
    fun init() {
        Files.createDirectories(Xdg.stateDir)
        System.setProperty("NNMA_LOG_DIR", Xdg.stateDir.toString())
    }
}
