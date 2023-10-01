@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(libs.plugins.kotlin.jvm)
}

val mainClassInManifest = "com.lorcanaapi.ApplicationKt"

group = "com.github.codlab.lorcana.server"
version = "1.0"

repositories {
    mavenCentral()
    google()
}

group = "com.lorcanaapi"
version = "0.0.1"

application {
    mainClass.set(mainClassInManifest)

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = mainClassInManifest
    }
}

dependencies {
    api(project(":lorcana-shared"))
    api(libs.kotlinx.coroutines.jvm)
    api(libs.fuzzywuzzy)
    api(libs.ktor.server.core)
    api(libs.ktor.server.netty)
    api(libs.ktor.server.contentnegociation)
    api(libs.ktor.server.json)
    api(libs.ktor.server.cors)
    testApi(libs.ktor.server.tests.jvm)
    testApi(kotlin("test"))

}
