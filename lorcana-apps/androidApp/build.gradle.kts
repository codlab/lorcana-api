import com.google.firebase.appdistribution.gradle.firebaseAppDistribution

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.jetbrains.compose)
    // alias(libs.plugins.android.google.services)
}


val originalVersion: String by rootProject.ext

android {
    compileSdk = dolbyio.versions.compileSdkVersion.get().toInt()
    namespace = "eu.codlab.lorcana.app"

    defaultConfig {
        applicationId = "eu.codlab.lorcana.app"
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${rootProject.ext.get("kotlinCompilerExtensionVersion")}"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles.add(getDefaultProguardFile("proguard-android.txt"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

dependencies {
    api(project(":lorcana-shared-ui"))
    implementation(libs.androidx.appcompat)

    implementation(platform("androidx.compose:compose-bom:2023.01.00"))
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:1.7.0")
}
