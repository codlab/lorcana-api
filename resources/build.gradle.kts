@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.moko.resources.generator)
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
        summary = "Some description for the Resources"
        homepage = "Link to the models"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../lorcana-apps/iosApp/Podfile")
        framework {
            baseName = "resources"
            isStatic = false
            embedBitcode("disable")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.coroutines)
                api(libs.moko.resources)
            }
        }
        val commonTest by getting {
        }

        val androidMain by getting {
            dependsOn(commonMain)
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        // part for macos
        val nativeMain by getting {
            dependsOn(commonMain)
        }

        val jvmMain by getting {
            dependsOn(commonMain)
        }

        val jsMain by getting {
            dependsOn(commonMain)
        }
    }
}

android {
    namespace = "eu.codlab.lorcana.resources"
    compileSdk = 33
    defaultConfig {
        minSdk = 23
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "eu.codlab.lorcana.resources"
    multiplatformResourcesClassName = "Resources" // optional, default MR
    multiplatformResourcesVisibility =
        dev.icerock.gradle.MRVisibility.Public
    iosBaseLocalizationRegion = "en"
}

tasks.register("generateMR") {
    group = "moko-resources"
    dependsOn("licenseCopy")
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { this.dependsOn(it) }
}

tasks.register("generateImages") {
    group = "moko-resources"
    val parent = file("${rootProject.projectDir}/src/data/images")
    var resources = file("${project.projectDir}/src/commonMain/resources/MR/images/")
    if (!resources.exists()) resources.mkdirs()

    parent.list()?.forEach {
        val original = File(parent, it)
        val newFile = File(resources, it)
        try {
            println("copy from ${original.absolutePath} to ${newFile.absolutePath}")
            original.copyTo(newFile, overwrite = false)
        } catch (e: FileAlreadyExistsException) {
            // nothing
        }
    }
}

tasks.register("concatenateMR") {
    dependsOn("generateImages")
    group = "moko-resources"
    val parent = file("${rootProject.projectDir}/src/data/cards")
    val array = parent.list()?.map{
        val current = File(parent.absolutePath, it)
        current.readText()
    } ?: mutableListOf()

    val concatenateText = array.joinToString( separator = ",", prefix = "[", postfix = "]")
    val concatenate = File(parent.absolutePath, "../allCards.txt")
    concatenate.writeText(concatenateText)

    tasks.findByName("generateMR")?.dependsOn(this)
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { this.dependsOn(it) }
}

val licenseCopy by tasks.registering(Copy::class) {
    dependsOn(":lorcana-shared-ui:licenseReleaseReport")
    from(layout.projectDirectory.file("../lorcana-shared-ui/build/reports/licenses/licenseReleaseReport.json"))
    into(layout.projectDirectory.file("src/commonMain/resources/MR/files/"))

    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { it.dependsOn(this) }

    tasks.matching { it.name.startsWith("process") && it.name.endsWith("JavaRes") }
        .forEach { it.dependsOn(this) }
}

afterEvaluate {
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { it.dependsOn(licenseCopy) }
}
