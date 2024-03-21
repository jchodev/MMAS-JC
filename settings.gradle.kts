pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://www.jitpack.io" ) }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "mmas-jc"
include(":app")
include(":core:designsystem")
include(":core:database")
include(":feature:input")
include(":feature:analysis")
include(":feature:calendar")
include(":feature:setting")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":feature:home")
include(":core:model")
include(":feature:nativelib")
