
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.getByType


class CommonModulePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        basicApply(project = project)
    }
}

class CommonModuleWithoutJetpackPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        basicApply(project = project, withJetpack = false)
    }
}


private fun basicApply(project: Project, withJetpack: Boolean = true){
    project.plugins.apply("kotlin-android")
    project.plugins.apply("kotlin-kapt")

    ////************* Make Junit test WORK!!**************
    project.tasks.withType(Test::class.java).configureEach {
        useJUnitPlatform()
    }

    // Access the version catalog
    val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

    val androidExtension = project.extensions.getByName("android")

    if (androidExtension is BaseExtension) {
        androidExtension.apply {
            compileSdkVersion(34)
            defaultConfig {
                targetSdk = 34
                minSdk = 24
                versionCode = 1
                versionName = "1.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            val proguardFile = "proguard-rules.pro"
            when (this) {
                is LibraryExtension -> defaultConfig {
                    // apply the pro guard rules for library
                    consumerProguardFiles("consumer-rules.pro")
                }
                is AppExtension -> buildTypes {

                    getByName("release") {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        debuggable(false)
                        proguardFile(proguardFile)
                    }
                    getByName("debug") {
                        isMinifyEnabled = false
                        isShrinkResources = false
                        debuggable(true)
                        proguardFile(proguardFile)
                    }
                }
            }
            composeOptions.kotlinCompilerExtensionVersion =  libs.findVersion("compose-compiler").get().requiredVersion
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            //make app/build.gradle.kts work!!
            packagingOptions {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }

            buildFeatures.buildConfig = true

            if (withJetpack) {
                buildFeatures.compose = true
            }

        }
    }
}