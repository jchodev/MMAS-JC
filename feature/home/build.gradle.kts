@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-plugin")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)

    id("kotlin-parcelize")
}

android {
    namespace = "com.jerryalberto.mmas.feature.home"

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))
    implementation(project(":core:database"))
    implementation(project(":core:testing"))

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

    //timber
    implementation(libs.timber)

    val vico_chart_version = "1.14.0"

    // For Jetpack Compose.
    implementation("com.patrykandpatrick.vico:compose:$vico_chart_version")
    // For `compose`. Creates a `ChartStyle` based on an M2 Material Theme.
    implementation("com.patrykandpatrick.vico:compose-m2:$vico_chart_version")
    // For `compose`. Creates a `ChartStyle` based on an M3 Material Theme.
    implementation("com.patrykandpatrick.vico:compose-m3:$vico_chart_version")
    // Houses the core logic for charts and other elements. Included in all other modules.
    implementation("com.patrykandpatrick.vico:core:$vico_chart_version")
    // For the view system.
    implementation("com.patrykandpatrick.vico:views:$vico_chart_version")
}