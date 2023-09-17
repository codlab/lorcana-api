@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.about.libraries)
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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.moko.resources)
                implementation(libs.moko.resources.compose)
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
    implementation(project(":kotlin-safearea"))

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
    api(libs.voyager.transitions)
    implementation(libs.moko.viewmodel)
    implementation(libs.moko.viewmodel.compose)
    implementation(libs.moko.resources)
    implementation(libs.moko.resources.compose)
    implementation(libs.about.libraries)
    implementation(libs.kamel.image)
}

// also putting the logic for the libraries here

aboutLibraries {
    registerAndroidTasks = false
    prettyPrint = true
}

multiplatformResources {
    multiplatformResourcesPackage = "com.github.codlab.lorcana.sharedui" // required
    multiplatformResourcesClassName = "Resources" // optional, default MR
    multiplatformResourcesVisibility =
        dev.icerock.gradle.MRVisibility.Public // optional, default Public
    iosBaseLocalizationRegion = "en" // optional, default "en"
}

val licenseCopy by tasks.registering(Copy::class) {
    dependsOn("licenseReleaseReport")
    from(layout.buildDirectory.file("reports/licenses/licenseReleaseReport.json"))
    into(layout.projectDirectory.file("src/commonMain/resources/MR/files/"))

    listOf(
        Pair("generateMore", "Main"),
        Pair("process", "JavaRes"),
        Pair("compile", "KotlinAndroid")
    ).forEach { pair ->
        tasks.matching { it.name.startsWith(pair.first) && it.name.endsWith(pair.second) }
            .forEach { it.dependsOn(this) }
    }
}

tasks.register("generateMR") {
    dependsOn(licenseCopy)
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { this.dependsOn(it) }

    listOf(
        Pair("generateMore", "Main"),
        Pair("process", "JavaRes"),
        Pair("compile", "KotlinAndroid")
    ).forEach { pair ->
        println("having to deal with $pair")
        tasks.matching { it.name.startsWith(pair.first) && it.name.endsWith(pair.second) }
            .forEach {
                println("task is ${it.name}")
                it.dependsOn(this)
            }
    }
}

afterEvaluate {
    tasks.matching { it.name.startsWith("generateMR") && it.name.endsWith("Main") }
        .forEach { it.dependsOn(licenseCopy) }
}
