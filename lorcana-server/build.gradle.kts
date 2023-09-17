@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.github.codlab.lorcana.server"
version = "1.0"

repositories {
    mavenCentral()
    google()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

dependencies {
    api(project(":lorcana-shared-ui"))
    api(libs.kotlinx.coroutines.jvm)
    api(libs.fuzzywuzzy)
}
