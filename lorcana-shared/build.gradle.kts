@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.moko.resources.generator)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
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

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.moko.resources)
                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.coroutines)
                api(project(":models"))

                api(libs.ktor.core)
                api(libs.ktor.logging)
                api(libs.ktor.serialization)
                api(libs.ktor.content.negotiation)
                api(libs.korio)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                api(libs.kotlinx.coroutines.test)
            }
        }

        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)

                api("androidx.core:core-ktx:1.9.0")
                api(libs.androidx.ui.tooling)
                api(libs.androidx.appcompat)
                api(libs.androidx.activity.compose)
                api(libs.insetx)

                api(libs.ktor.okhttp)
                api(libs.kotlinx.coroutines.android)

                api(libs.korio.android)
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
                implementation(libs.ktor.darwin)
            }
        }

        // part for macos
        val nativeMain by getting {
            dependsOn(commonMain)
        }

        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                api(libs.korio.jvm)
                api(libs.ktor.apache5)
            }
        }

        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                api(libs.korio.js)
            }
        }
    }
}

android {
    namespace = "com.github.codlab.lorcana"
    compileSdk = 33
    defaultConfig {
        minSdk = 23
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${rootProject.ext.get("kotlinCompilerExtensionVersion")}"
    }
    sourceSets["main"].kotlin.srcDirs(
        "src/androidMain/kotlin",
        "build/generated/moko/androidMain/src"
    )
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}

multiplatformResources {
    multiplatformResourcesPackage = "com.github.codlab.lorcana.shared"
    multiplatformResourcesClassName = "SharedRes" // optional, default MR
    multiplatformResourcesVisibility =
        dev.icerock.gradle.MRVisibility.Public
    iosBaseLocalizationRegion = "en"
}

tasks.register("generateMR") {
    group = "moko-resources"
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { this.dependsOn(it) }
}

tasks.register("concatenateMR") {
    group = "moko-resources"
    val parent = file("${rootProject.projectDir}/assets/data/cards")
    val array = parent.list()?.map {
        val current = File(parent.absolutePath, it)
        current.readText()
    } ?: mutableListOf()

    val concatenateText = array.joinToString(separator = ",", prefix = "[", postfix = "]")
    val concatenate = File(parent.absolutePath, "../allCards.txt")
    concatenate.writeText(concatenateText)

    tasks.findByName("generateMR")?.dependsOn(this)
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { this.dependsOn(it) }
}
