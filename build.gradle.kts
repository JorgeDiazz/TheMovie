import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
  }
  dependencies {
    classpath("com.android.tools.build:gradle:${Versions.gradle}")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
    classpath("org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}")
    classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.gradleKlint}")
    classpath("de.mannodermaus.gradle.plugins:android-junit5:${Versions.androidJUnit5}")
    classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}")
    classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}")
  }
}

plugins {
  id("org.sonarqube") version Versions.sonarqube
  id("org.jetbrains.dokka") version Versions.dokka
}

allprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
  }
}

subprojects {

  tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
  }

  tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
  }

  apply(plugin = "org.jetbrains.dokka")
}

tasks.dokkaHtmlMultiModule.configure {
  outputDirectory.set(buildDir.resolve("dokkaCustomMultiModuleOutput"))
}

tasks.register("clean", Delete::class.java) {
  delete(rootProject.buildDir)
}
