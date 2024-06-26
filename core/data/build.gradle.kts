@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-without-jetpack-plugin")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)
}


android {
    namespace = "com.jerryalberto.mmas.core.data"
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))


    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

    //room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    //timber
    implementation(libs.timber)

    //junit5
    testImplementation(libs.bundles.junit5.test.implementation)
    testRuntimeOnly(libs.bundles.junit5.test.runtime.only)
    testImplementation(project(":core:testing"))
}