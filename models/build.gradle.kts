@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("app.cash.sqldelight") version "2.0.0"
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.kotlin.plugin.serialization)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("eu.codlab.lorcana.models")
        }
    }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.java.get()
            }
        }
    }

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = libs.versions.java.get()
        }
    }

    js(IR) {
        browser()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Models"
        homepage = "Link to the models"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../lorcana-apps/iosApp/Podfile")
        framework {
            baseName = "models"
            isStatic = false
            embedBitcode("disable")
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:runtime:2.0.0")
                api(libs.kotlinx.coroutines)
            }
        }
        val commonTest by getting {
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("app.cash.sqldelight:android-driver:2.0.0")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.0")
            }
        }

        // part for macos
        val nativeMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.0")
            }
        }

        val jvmMain by getting {
            dependsOn(commonMain)

            dependencies {
                implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
            }
        }

        val jsMain by getting {
            dependsOn(commonMain)

            dependencies {
                implementation("app.cash.sqldelight:web-worker-driver:2.0.0")
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework> {
            linkerOpts.add("-lsqlite3")
        }
    }
}

android {
    namespace = "eu.codlab.lorcana.models"
    compileSdk = 33
    defaultConfig {
        minSdk = 23
    }
}
