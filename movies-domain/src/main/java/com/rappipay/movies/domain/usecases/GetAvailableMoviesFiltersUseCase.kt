package com.rappipay.movies.domain.usecases

import com.app.base.interfaces.FlowUseCase
import com.rappipay.movies.domain.model.MoviesFilters
import com.rappipay.movies.domain.repositories.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that gets available movies filters.
 *
 */
class GetAvailableMoviesFiltersUseCase @Inject constructor(
  private val moviesRepository: IMoviesRepository,
) : FlowUseCase<Unit, MoviesFilters>() {

  override suspend fun execute(input: Unit): Flow<MoviesFilters> {
    return moviesRepository.getAvailableMoviesFilters()
  }
}
