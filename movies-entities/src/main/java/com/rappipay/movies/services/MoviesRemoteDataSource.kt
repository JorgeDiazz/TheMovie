package com.rappipay.movies.services

import com.app.core.network.BaseApiResponse
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(private val moviesService: MoviesService) : BaseApiResponse(), IMoviesRemoteDataSource {
  override suspend fun fetchUpcomingMovies(page: Int) = safeApiCall { moviesService.fetchUpcomingMovies(page) }

  override suspend fun fetchTopRatedMovies(page: Int) = safeApiCall { moviesService.fetchTopRatedMovies(page) }
}
