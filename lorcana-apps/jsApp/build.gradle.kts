@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.moko.resources.generator)
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":lorcana-shared-ui"))
                api(libs.look.and.feel)
                api(compose.runtime)
                api(compose.ui)
                api(compose.foundation)
                api(libs.moko.resources)
            }
        }

        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                api(compose.html.core)
                implementation("app.cash.sqldelight:web-worker-driver:2.0.0")
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }
    }
}

compose.experimental {
    web.application {}
}

multiplatformResources {
    multiplatformResourcesPackage = "eu.codlab.lorcana.js"
    multiplatformResourcesClassName = "Resources" // optional, default MR
    multiplatformResourcesVisibility =
        dev.icerock.gradle.MRVisibility.Public
}