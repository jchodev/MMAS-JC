

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    //alias(libs.plugins.android.application)
    //alias(libs.plugins.android.kotlin)

    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)

    id("module-plugin")
}


android {
    namespace = "com.jerryalberto.mmas"
    defaultConfig {
        applicationId = "com.jerryalberto.mmas"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.jerryalberto.mmas"
//        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary = true
//        }
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//    kotlinOptions {
//        jvmTarget = "17"
//    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion =  libs.versions.compose.compiler.get()
//    }

}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":feature:input"))
    implementation(project(":feature:analysis"))
    implementation(project(":feature:calendar"))
    implementation(project(":feature:setting"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.androidx.compose)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

    //junit5
    testImplementation (libs.junit.jupiter.api)
    testImplementation (libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.vintage.engine)

    //timber
    implementation(libs.timber)
}