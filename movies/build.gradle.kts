plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id("dagger.hilt.android.plugin")
  id("androidx.navigation.safeargs")
  id("kotlin-parcelize")
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
  implementation(project(":components"))
  implementation(project(":core"))
  implementation(project(":base"))

  implementation(project(":movies-domain"))
  implementation(project(":movies-entities"))

  implementation(Libraries.kotlinJDK)
  implementation(Libraries.appcompat)
  implementation(Libraries.androidXCore)
  implementation(Libraries.constraintLayout)
  implementation(Libraries.material)
  implementation(Libraries.fragmentKtx)

  implementation(Libraries.navigationFragment)
  implementation(Libraries.navigationUi)

  implementation(Libraries.lifeCycleViewModel)
  implementation(Libraries.lifeCycleViewModelKtx)

  implementation(Libraries.roomRuntime)
  implementation(Libraries.roomKtx)
  kapt(Libraries.roomCompiler)

  implementation(Libraries.coil)

  implementation(AnnotationProcessors.daggerHilt)
  kapt(AnnotationProcessors.daggerHiltAndroidCompiler)
  implementation(AnnotationProcessors.daggerHiltViewModel)

  implementation(Libraries.paging)

  kapt(Libraries.kotlinxMetadata)

  implementation(Libraries.retrofit)

  implementation(Libraries.moshi)
  kapt(AnnotationProcessors.moshiCodegen)

  implementation(Libraries.youTubePlayer)

  androidTestImplementation(Libraries.jUnitExtKtx)
  androidTestImplementation(Libraries.espressoCore)
}
