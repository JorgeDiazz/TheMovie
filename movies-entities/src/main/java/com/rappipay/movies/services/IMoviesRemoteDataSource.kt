package com.rappipay.movies.services

import com.app.core.network.NetworkState
import com.rappipay.movies.entities.remote.movies.MoviesListRemote
import com.rappipay.movies.entities.remote.movies.videos.MovieVideosDataListRemote

interface IMoviesRemoteDataSource {
  suspend fun fetchUpcomingMovies(page: Int): NetworkState<MoviesListRemote>

  suspend fun fetchTopRatedMovies(page: Int): NetworkState<MoviesListRemote>

  suspend fun fetchMovieVideosData(movieId: Int): NetworkState<MovieVideosDataListRemote>
}
