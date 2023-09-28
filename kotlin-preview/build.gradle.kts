@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

group = "fr.test"

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
            //
        }
        val commonTest by getting

        val jsMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val jvmMain by getting
        val macosX64Main by getting
        val macosArm64Main by getting
        val linuxX64Main by getting
        val linuxArm64Main by getting
        val mingwX64Main by getting

        listOf(
            jsMain,
            iosX64Main,
            iosArm64Main,
            iosSimulatorArm64Main,
            jvmMain,
            macosX64Main,
            macosArm64Main,
            linuxX64Main,
            linuxArm64Main,
            mingwX64Main,
            jsMain
        ).forEach {
            it.dependsOn(commonMain)
        }

        val androidMain by getting {
            dependsOn.minus(commonMain).minus(jvmMain)
            dependencies {
                api(libs.androidx.ui.tooling)
                api(libs.androidx.ui.tooling.preview)
            }
        }
        val androidUnitTest by getting
    }
}

android {
    namespace = "androidx.compose.ui.tooling.preview.stub"
    compileSdk = 33
}
