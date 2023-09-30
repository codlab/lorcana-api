package com.github.codlab.lorcana

enum class Platforms {
    Android,
    IOS,
    JVM,
    JS,
    OTHER
}

expect fun currentPlatform(): Platforms
