package com.rappipay.movies.di

import androidx.paging.PagingData
import com.app.base.interfaces.FlowUseCase
import com.rappipay.movies.domain.model.Movie
import com.rappipay.movies.domain.model.MoviesFilters
import com.rappipay.movies.domain.usecases.GetAvailableMoviesFiltersUseCase
import com.rappipay.movies.domain.usecases.GetSuggestedMoviesUseCase
import com.rappipay.movies.domain.usecases.GetTopRatedMoviesUseCase
import com.rappipay.movies.domain.usecases.GetUpcomingMoviesUseCase
import com.rappipay.movies.qualifiers.GetAvailableMoviesFilters
import com.rappipay.movies.qualifiers.GetSuggestedMovies
import com.rappipay.movies.qualifiers.GetTopRatedMovies
import com.rappipay.movies.qualifiers.GetUpcomingMovies
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [MoviesRepositoryModule::class])
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {

  @Binds
  @GetUpcomingMovies
  @Singleton
  abstract fun bindsGetUpcomingMoviesUseCase(useCase: GetUpcomingMoviesUseCase): FlowUseCase<Unit, PagingData<Movie>>

  @Binds
  @GetTopRatedMovies
  @Singleton
  abstract fun bindsGetTopRatedMoviesUseCase(useCase: GetTopRatedMoviesUseCase): FlowUseCase<Unit, PagingData<Movie>>

  @Binds
  @GetAvailableMoviesFilters
  @Singleton
  abstract fun bindsGetAvailableMoviesFiltersUseCase(useCase: GetAvailableMoviesFiltersUseCase): FlowUseCase<Unit, MoviesFilters>

  @Binds
  @GetSuggestedMovies
  @Singleton
  abstract fun bindsGetSuggestedMoviesUseCase(useCase: GetSuggestedMoviesUseCase): FlowUseCase<Pair<String?, Int?>, List<Movie>>
}
