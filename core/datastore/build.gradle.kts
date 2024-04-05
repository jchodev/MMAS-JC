@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-without-jetpack-plugin")

    id("kotlin-parcelize")

    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)

    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.jerryalberto.mmas.core.datastore"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

    implementation(libs.timber)

    //datastore
    implementation (libs.androidx.datastore.core)
    implementation (libs.androidx.datastore)

    implementation( libs.kotlinx.serialization.json)
}