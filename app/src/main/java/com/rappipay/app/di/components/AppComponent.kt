package com.rappipay.app.di.components

import android.app.Application
import com.app.core.CoreComponent
import com.rappipay.app.di.modules.ActivityAggregatorModule
import com.rappipay.app.di.modules.BaseModule
import com.rappipay.app.di.modules.CacheModule
import com.rappipay.app.di.modules.FragmentModule
import com.rappipay.app.di.modules.LoggerModule
import com.rappipay.app.di.modules.NetworkModule
import com.rappipay.app.di.modules.RappiPayAppModule
import com.rappipay.movies.di.MoviesModule
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * Represents the modules provided to the app.
 *
 */
@InstallIn(SingletonComponent::class)
@EntryPoint
@ExperimentalSerializationApi
@Component(
  modules = [
    BaseModule::class,
    NetworkModule::class,
    ActivityAggregatorModule::class,
    CacheModule::class,
    FragmentModule::class,
    LoggerModule::class,
    RappiPayAppModule::class,
    MoviesModule::class,
  ]
)
interface AppComponent : CoreComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }
}
