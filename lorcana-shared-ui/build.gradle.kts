@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.moko.resources.generator)
    alias(libs.plugins.about.libraries)
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

    val name = Attribute.of("name", String::class.java)
    val dummy = Attribute.of("dummy", String::class.java)
    listOf(
        iosX64 {
            attributes.attribute(dummy, "x64")
        },
        iosArm64 {
            attributes.attribute(dummy, "arm64")
        },
        iosSimulatorArm64 {
            attributes.attribute(dummy, "simArm64")
        }
    )

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../lorcana-apps/iosApp/Podfile")
        framework {
            baseName = "lorcana_shared_ui"
            isStatic = false
            embedBitcode("disable")
            linkerOpts.add("-lsqlite3")
        }
        extraSpecAttributes["resources"] =
            "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    configurations.configureEach {
        this.attributes.attribute(name, this.name)
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":lorcana-shared"))
                implementation(project(":kotlin-preview"))
                api(project(":kotlin-safearea"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation(libs.voyager.navigator)
                implementation(libs.voyager.tab.navigator)
                implementation(libs.voyager.transitions)
                api(libs.look.and.feel)
                implementation(libs.moko.viewmodel)
                implementation(libs.moko.viewmodel.compose)
                implementation(libs.moko.resources)
                implementation(libs.moko.resources.compose)

                implementation(libs.about.libraries)
                implementation(libs.kamel.image)

                implementation(libs.ktor.core)
                implementation(libs.fuzzywuzzy.multiplatform)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                api("androidx.core:core-ktx:1.9.0")
                api(libs.androidx.appcompat)
                api(libs.androidx.activity.compose)
                api(libs.insetx)

                implementation(libs.ktor.okhttp)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                implementation(libs.insetx)
                implementation(libs.ktor.darwin)
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.insetx)
                implementation(libs.ktor.apache5)
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
    namespace = "com.github.codlab.lorcana.sharedui"
    //compileSdk = 33
    //defaultConfig {
    //    minSdk = 23
    //}
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${rootProject.ext.get("kotlinCompilerExtensionVersion")}"
    }
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}

dependencies {
    implementation(libs.androidx.window)
}

aboutLibraries {
    registerAndroidTasks = false
    prettyPrint = true
}

tasks.register("concatenateMR") {
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
        } catch(e: FileAlreadyExistsException) {
            // nothing
        }
    }

    tasks.findByName("generateMR")?.dependsOn(this)
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { this.dependsOn(it) }
}

val licenseCopy by tasks.registering(Copy::class) {
    dependsOn("licenseReleaseReport")
    from(layout.buildDirectory.file("reports/licenses/licenseReleaseReport.json"))
    into(layout.projectDirectory.file("src/commonMain/resources/MR/files/"))

    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { it.dependsOn(this) }

    tasks.matching { it.name.startsWith("process") && it.name.endsWith("JavaRes") }
        .forEach { it.dependsOn(this) }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.github.codlab.lorcana.sharedui" // required
    multiplatformResourcesClassName = "Resources" // optional, default MR
    multiplatformResourcesVisibility =
        dev.icerock.gradle.MRVisibility.Public // optional, default Public
    iosBaseLocalizationRegion = "en" // optional, default "en"
}

tasks.register("generateMR") {
    group = "moko-resources"
    dependsOn("licenseCopy")
}

afterEvaluate {
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { it.dependsOn(licenseCopy) }
}
