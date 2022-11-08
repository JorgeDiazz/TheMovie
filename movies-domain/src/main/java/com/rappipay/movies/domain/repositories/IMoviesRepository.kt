package com.rappipay.movies.domain.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rappipay.movies.domain.model.Movie
import com.rappipay.movies.domain.model.MovieVideoData
import com.rappipay.movies.domain.model.MoviesFilters
import com.rappipay.movies.repositories.MoviesPagingSource
import kotlinx.coroutines.flow.Flow

/**
 * Represents the interface repository for movies.
 *
 */
interface IMoviesRepository {
  fun getUpcomingMovies(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Movie>>

  fun getTopRatedMovies(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Movie>>

  private fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(
      pageSize = MoviesPagingSource.DEFAULT_PAGE_SIZE,
      prefetchDistance = MoviesPagingSource.DEFAULT_PAGE_SIZE / 3,
      enablePlaceholders = false
    )
  }

  fun getAvailableMoviesFilters(): Flow<MoviesFilters>

  fun getSuggestedMoviesAvailableMoviesFilters(languageIsoCode: String?, releaseYear: Int?): Flow<List<Movie>>

  fun getMovieVideoData(movieId: Int): Flow<MovieVideoData>
}
