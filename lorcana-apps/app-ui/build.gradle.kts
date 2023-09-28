@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.moko.resources)
                implementation(libs.moko.resources.compose)
                implementation(libs.moko.resources.ext)
                api(libs.fuzzywuzzy.multiplatform)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
        }
        val jvmMain by getting {
            dependsOn(commonMain)
        }
    }
}

android {
    compileSdk = dolbyio.versions.compileSdkVersion.get().toInt()
    namespace = "eu.codlab.lorcana.shared"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =
            rootProject.ext.get("kotlinCompilerExtensionVersion") as String
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":lorcana-shared"))
    implementation(project(":kotlin-preview"))
    implementation(project(":resources"))

    implementation(libs.safearea)
    implementation(libs.collapsing.toolbar)

    implementation(libs.androidx.appcompat)
    implementation(platform("androidx.compose:compose-bom:2023.01.00"))
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    //implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation(libs.androidx.activity.compose)

    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    implementation(compose.components.resources)

    api(libs.voyager.navigator)
    api(libs.voyager.tab.navigator)
    api(libs.voyager.transitions)
    api(libs.look.and.feel)
    implementation(libs.moko.viewmodel)
    implementation(libs.moko.viewmodel.compose)
    implementation(libs.moko.resources)
    implementation(libs.moko.resources.compose)
    implementation(libs.about.libraries)
    implementation(libs.kamel.image)
}
