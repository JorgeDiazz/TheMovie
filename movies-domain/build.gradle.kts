plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
}

android {
  compileSdk = Api.compileSDK
  defaultConfig {
    minSdk = Api.minSDK
    targetSdk = Api.targetSDK
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  lint {
    abortOnError = false
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  buildFeatures {
    viewBinding = true
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

dependencies {
  implementation(Libraries.kotlinJDK)

  implementation(project(":base"))
  implementation(project(":core"))
  implementation(project(":movies-entities"))

  implementation(Libraries.paging)

  implementation(AnnotationProcessors.daggerHilt)
  kapt(AnnotationProcessors.daggerHiltAndroidCompiler)
  implementation(AnnotationProcessors.daggerHiltViewModel)

  Libraries.suiteTest.forEach { testImplementation(it) }
}
