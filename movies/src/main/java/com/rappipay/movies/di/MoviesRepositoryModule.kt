package com.rappipay.movies.di

import android.content.Context
import androidx.room.Room
import com.app.core.di.RetrofitNullSerializationEnabled
import com.rappipay.movies.domain.repositories.IMoviesRepository
import com.rappipay.movies.domain.repositories.MoviesRepository
import com.rappipay.movies.room.database.MoviesDatabase
import com.rappipay.movies.services.IMoviesRemoteDataSource
import com.rappipay.movies.services.MoviesRemoteDataSource
import com.rappipay.movies.services.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesRepositoryModule {
  @Provides
  @Singleton
  fun providesMoviesService(
    @RetrofitNullSerializationEnabled retrofit: Retrofit
  ): MoviesService {
    return retrofit.create(MoviesService::class.java)
  }

  @Provides
  @Singleton
  fun providesMoviesRepository(
    context: Context,
    moviesRemoteDataSource: IMoviesRemoteDataSource,
  ): IMoviesRepository {
    val database =
      Room.databaseBuilder(context, MoviesDatabase::class.java, "movies-database")
        .fallbackToDestructiveMigration()
        .build()

    return MoviesRepository(moviesRemoteDataSource, database)
  }

  @Provides
  @Singleton
  fun providesMoviesRemoteDataSource(
    moviesRemoteDataSource: MoviesRemoteDataSource
  ): IMoviesRemoteDataSource = moviesRemoteDataSource
}
