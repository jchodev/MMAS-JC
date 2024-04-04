

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
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":feature:home"))
    implementation(project(":feature:input"))
    implementation(project(":feature:analysis"))
    implementation(project(":feature:calendar"))
    implementation(project(":feature:setting"))
    implementation(project(":feature:transaction"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)

    //splashscreen
    implementation (libs.androidx.splash)

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