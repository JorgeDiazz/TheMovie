package com.rappipay.app.di.modules

import com.app.base.interfaces.Logger
import com.rappipay.app.RappiPayDebugTree
import com.rappipay.app.RappiPayLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {
  @Provides
  @Singleton
  fun providesLoggerImplementation(): Logger {
    val tree = RappiPayDebugTree() // The logger could be changed according to current environment
    return RappiPayLogger(tree)
  }
}
