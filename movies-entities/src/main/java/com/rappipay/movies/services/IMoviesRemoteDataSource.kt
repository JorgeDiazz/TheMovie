package com.rappipay.movies.services

import com.app.core.network.NetworkState
import com.rappipay.movies.entities.remote.movies.MoviesListRemote

interface IMoviesRemoteDataSource {
  suspend fun fetchUpcomingMovies(page: Int): NetworkState<MoviesListRemote>

  suspend fun fetchTopRatedMovies(page: Int): NetworkState<MoviesListRemote>
}
