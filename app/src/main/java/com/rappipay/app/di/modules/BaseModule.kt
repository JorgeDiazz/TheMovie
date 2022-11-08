package com.rappipay.app.di.modules

import android.app.Application
import android.content.Context
import com.app.core.interfaces.AppResources
import com.rappipay.app.data.RappiPayResources
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseModule {
  @Binds
  abstract fun bindContext(rappipayApp: Application): Context

  @Binds
  abstract fun bindResources(rappipayResources: RappiPayResources): AppResources
}
