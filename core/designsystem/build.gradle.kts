@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    //alias(libs.plugins.android.library)
    //alias(libs.plugins.android.kotlin)

    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    id("module-plugin")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)
}

android {
    namespace = "com.jerryalberto.mmas.core.designsystem"
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)

    //jetpack compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.androidx.compose)
    debugImplementation(libs.compose.ui.tooling.debug)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

    //Calendar picker dialog material
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

}