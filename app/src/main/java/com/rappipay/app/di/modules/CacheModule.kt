package com.rappipay.app.di.modules

import android.content.Context
import com.app.base.interfaces.Cache
import com.app.core.data.SharedPreferencesCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
  @Provides
  @Singleton
  fun providesCacheImplementation(context: Context): Cache {
    val preferencesName = "general-cache"
    return SharedPreferencesCache(preferencesName, context.applicationContext)
  }
}
