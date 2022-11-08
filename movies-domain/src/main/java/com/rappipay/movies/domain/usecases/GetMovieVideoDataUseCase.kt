package com.rappipay.movies.domain.usecases

import com.app.base.interfaces.FlowUseCase
import com.rappipay.movies.domain.model.MovieVideoData
import com.rappipay.movies.domain.repositories.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that gets information to access movies' trailers.
 *
 */
class GetMovieVideoDataUseCase @Inject constructor(
  private val moviesRepository: IMoviesRepository,
) : FlowUseCase<Int, MovieVideoData>() {

  override suspend fun execute(input: Int): Flow<MovieVideoData> {
    return moviesRepository.getMovieVideoData(input)
  }
}
