plugins {
  id("com.android.library")
  kotlin("android")
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
  implementation(project(":base"))

  implementation(Libraries.multidex)

  implementation(Libraries.kotlinJDK)

  implementation(Libraries.retrofit)
  implementation(Libraries.retrofitMoshi)
  implementation(platform(Libraries.okHttpBoM))
  implementation(Libraries.okHttpInterceptor)
  implementation(Libraries.moshi)

  implementation(Libraries.javaInject)
}
