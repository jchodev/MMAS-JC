@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-without-jetpack-plugin")

}

android {
    namespace = "com.jerryalberto.mmas.core.testing"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
}