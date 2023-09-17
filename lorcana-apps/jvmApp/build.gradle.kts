import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.jetbrains.compose)
}

group = "com.github.codlab.lorcana.app"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

tasks.register("buildAndNotarizeDmg") {
    dependsOn("packageReleaseDmg", "notarizeDmg")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

dependencies {
    api(project(":lorcana-shared-ui"))
    api(libs.jetbrains.compose.runtime)
    api(libs.jetbrains.compose.ui)
    api(libs.jetbrains.compose.foundation.layout)
    api(libs.jetbrains.compose.material)
    api(libs.kotlinx.coroutines.jvm)
    api(compose.desktop.currentOs)
}

compose.desktop {
    application {
        buildTypes.release {
            proguard {
                configurationFiles.from("proguard-rules.pro")
            }
        }

        mainClass = "MainApplicationKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.github.codlab.lorcana.app"
            packageVersion = "1.0.0"
            /*windows {
                iconFile.set(project.file("icon.ico"))
            }
            */
            linux {
                iconFile.set(project.file("icon.png"))
            }

            macOS {
                iconFile.set(project.file("icon.icns"))

                //https://github.com/JetBrains/compose-jb/blob/master/tutorials/Signing_and_notarization_on_macOS/README.md
                if (rootProject.ext.get("MacOSSigningSign") as Boolean) {
                    System.out.println("signing for macos ${rootProject.ext.get("MacOSNotarizationAppleID") as String}")
                    bundleID = rootProject.ext.get("MacOSbundleID") as String

                    signing {
                        sign.set(rootProject.ext.get("MacOSSigningSign") as Boolean)
                        identity.set(rootProject.ext.get("MacOSSigningIdentity") as String)

                        if (null != rootProject.ext.get("MacOSSigningKeychain")) {
                            keychain.set(rootProject.ext.get("MacOSSigningKeychain") as String)
                        }
                    }

                    appStore = rootProject.ext.get("MacOSAppStore") as Boolean

                    notarization {
                        appleID.set(rootProject.ext.get("MacOSNotarizationAppleID") as String)
                        password.set(rootProject.ext.get("MacOSNotarizationPassword") as String)

                        // optional
                        ascProvider.set(rootProject.ext.get("MacOSNotarizationTeamID") as String)
                    }
                }

                infoPlist {
                    extraKeysRawXml = macExtraPlistKeys
                }
            }
        }
    }
}
val macExtraPlistKeys: String
    get() = """
<key>CFBundleLocalizations</key><array>
<string>en</string>
<string>fr</string>
</array>
"""
