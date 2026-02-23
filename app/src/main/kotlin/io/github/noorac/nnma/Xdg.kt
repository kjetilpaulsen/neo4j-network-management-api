package io.github.noorac.nnma

import java.nio.file.Path

object Xdg {
    private val home: Path = Path.of(System.getProperty("user.home"))

    val configHome: Path = 
        System.getenv("XDG_CONFIG_HOME")?.let(Path::of)
            ?: home.resolve(".config")

    val stateHome: Path = 
        System.getenv("XDG_STATE_HOME")?.let(Path::of)
            ?: home.resolve(".local/state")

    val dataHome: Path = 
        System.getenv("XDG_DATA_HOME")?.let(Path::of)
            ?: home.resolve(".local/share")

    val cacheHome: Path = 
        System.getenv("XDG_CACHE_HOME")?.let(Path::of)
            ?: home.resolve(".cache")

    val configDir: Path = configHome.resolve(AppInfo.APP_ID)
    val stateDir: Path = stateHome.resolve(AppInfo.APP_ID)
    val dataDir: Path = dataHome.resolve(AppInfo.APP_ID)
    val cacheDir: Path = cacheHome.resolve(AppInfo.APP_ID)
}

