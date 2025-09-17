// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id("com.android.application") version "8.6.0" apply false
  id("com.android.library") version "8.6.0" apply false
  id("org.jetbrains.kotlin.android") version "2.0.21" apply false
  id("com.mikepenz.aboutlibraries.plugin") version "11.2.3" apply false
}

allprojects {
  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "17"
    }
  }
}

tasks.register<Delete>("clean") {
  delete(rootProject.buildDir)
}
