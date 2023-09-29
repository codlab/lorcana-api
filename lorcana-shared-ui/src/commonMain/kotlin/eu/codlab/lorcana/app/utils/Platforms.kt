package eu.codlab.lorcana.app.utils

enum class Platforms {
    Android,
    IOS,
    JVM,
    JS,
    OTHER
}

expect fun currentPlatform(): Platforms