pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url= "https://oss.sonatype.org/content/repositories/snapshots/")
  }
}

rootProject.name = "Sparkles-App"

include(":app")
include(":peekandpop")
include(":maskable")
include(":fastui")
include(":filetree")
include(":java-compiler")