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
    applicationDefaultJvmArgs = listOf(
        "-Dio.ktor.development=$isDevelopment",
        "-DrootPath=${rootProject.projectDir.absoluteFile}")
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

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = mainClassInManifest
        attributes["RootPath"] = rootProject.projectDir.absoluteFile
    }
}

dependencies {
    api(project(":lorcana-shared"))
    api(libs.jcabi.manifest.reader)
    api(libs.kotlinx.coroutines.jvm)
    testApi(kotlin("test"))

    api(libs.ktor.core)
    api(libs.ktor.content.negotiation)
    api(libs.ktor.cio)
    api(libs.thumbnails)
}
