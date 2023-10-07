@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.plugin.serialization)
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
    }

    configurations.configureEach {
        this.attributes.attribute(name, this.name)
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":lorcana-shared"))
                implementation(project(":resources"))
                implementation(project(":kotlin-preview"))
                api(libs.safearea)
                api(libs.collapsing.toolbar)
                implementation(libs.widgets.compose)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                api(libs.look.and.feel)
                implementation(libs.moko.viewmodel)
                implementation(libs.moko.viewmodel.compose)
                implementation(libs.moko.resources.compose)
                implementation(libs.moko.resources.ext)

                implementation(libs.about.libraries)
                implementation(libs.kamel.image)
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
                implementation(libs.androidx.window)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                implementation(libs.insetx)
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${rootProject.ext.get("kotlinCompilerExtensionVersion")}"
    }
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

aboutLibraries {
    registerAndroidTasks = false
    prettyPrint = true
}
