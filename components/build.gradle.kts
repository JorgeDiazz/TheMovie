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

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  kapt(Libraries.lifeCycleCompiler)

  implementation(Libraries.multidex)

  implementation(Libraries.kotlinJDK)
  implementation(Libraries.appcompat)
  implementation(Libraries.fragmentKtx)
  implementation(Libraries.activityKtx)
  implementation(Libraries.androidXCore)
  implementation(Libraries.lifeCycleCommonJava8)
  implementation(Libraries.constraintLayout)
  implementation(Libraries.material)

  implementation(Libraries.swipeRefreshLayout)
}
