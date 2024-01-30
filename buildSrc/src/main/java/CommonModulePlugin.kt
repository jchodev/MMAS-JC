
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile


class CommonModulePlugin: Plugin<Project> {

    override fun apply(project: Project) {

        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-kapt")

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

                buildFeatures.compose = true

            }
        }

    }

}