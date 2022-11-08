package com.rappipay.movies.domain.usecases

import com.app.base.interfaces.FlowUseCase
import com.rappipay.movies.domain.model.Movie
import com.rappipay.movies.domain.repositories.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that gets suggested movies.
 *
 */
class GetSuggestedMoviesUseCase @Inject constructor(
  private val moviesRepository: IMoviesRepository,
) : FlowUseCase<Pair<@JvmSuppressWildcards String?, @JvmSuppressWildcards Int?>, List<@JvmSuppressWildcards Movie>>() {

  override suspend fun execute(input: Pair<String?, Int?>): Flow<List<Movie>> {
    val (languageIsoCode, releaseYear) = input
    return moviesRepository.getSuggestedMoviesAvailableMoviesFilters(languageIsoCode, releaseYear)
  }
}
