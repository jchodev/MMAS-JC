plugins {
    `kotlin-dsl`
}
repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.kgp)
    implementation(libs.agp)
    implementation(libs.squareup.javapoet) // fix https://github.com/google/dagger/issues/3068 issues
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    plugins {
        register("module-plugin") {
            id = "module-plugin"
            implementationClass = "CommonModulePlugin"
        }

        register("module-without-jetpack-plugin") {
            id = "module-without-jetpack-plugin"
            implementationClass = "CommonModuleWithoutJetpackPlugin"
        }
    }
}