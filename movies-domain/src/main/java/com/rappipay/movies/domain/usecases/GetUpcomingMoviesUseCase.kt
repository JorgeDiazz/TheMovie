package com.rappipay.movies.domain.usecases

import androidx.paging.PagingData
import com.app.base.interfaces.FlowUseCase
import com.rappipay.movies.domain.model.Movie
import com.rappipay.movies.domain.repositories.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that gets upcoming movies.
 *
 */
class GetUpcomingMoviesUseCase @Inject constructor(
  private val moviesRepository: IMoviesRepository,
) : FlowUseCase<Unit, PagingData<Movie>>() {

  override suspend fun execute(input: Unit): Flow<PagingData<Movie>> {
    return moviesRepository.getUpcomingMovies()
  }
}
