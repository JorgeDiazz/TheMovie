package com.rappipay.movies.domain.usecases

import com.app.base.interfaces.FlowUseCase
import com.rappipay.movies.domain.model.MovieVideoData
import com.rappipay.movies.domain.repositories.IMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * Represents the use case that gets available movies filters.
 *
 */
class GetMovieVideoDataUseCase @Inject constructor(
  private val moviesRepository: IMoviesRepository,
) : FlowUseCase<Int, MovieVideoData>() {

  override suspend fun execute(input: Int): Flow<MovieVideoData> {
    return moviesRepository.getMovieVideoData(input)
  }
}
