@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {
    androidTarget {
        publishAllLibraryVariants()

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

    macosX64()
    macosArm64()
    mingwX64()
    linuxX64()
    linuxArm64()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            // nothing
        }
        val commonTest by getting

        val androidMain by getting {
            dependsOn.minus(commonMain)
            dependencies {
                implementation(libs.androidx.ui.tooling)
                implementation(libs.androidx.ui.tooling.preview)
            }
        }
        val jsMain by getting {
            dependsOn(commonMain)
        }
        val androidUnitTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val jvmMain by getting {
            dependsOn(commonMain)
        }

        val macosX64Main by getting {}
        val macosArm64Main by getting {}
        val linuxX64Main by getting {}
        val linuxArm64Main by getting {}
        val mingwX64Main by getting {}
        macosX64Main.dependsOn(commonMain)
        macosArm64Main.dependsOn(commonMain)
        linuxX64Main.dependsOn(commonMain)
        linuxArm64Main.dependsOn(commonMain)
        mingwX64Main.dependsOn(commonMain)
    }
}

android {
    namespace = "androidx.compose.ui.tooling.preview.stub"
    compileSdk = 33
}

kotlin {
    cocoapods {
        summary = rootProject.extra.get("pomDescription").toString()
        homepage = rootProject.extra.get("pomScmUrl").toString()
        version = rootProject.extra.get("version").toString()
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "kotlin-preview-pod"
            embedBitcode("disable")
        }
    }
}
