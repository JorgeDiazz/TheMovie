package com.rappipay.movies.services

import com.rappipay.movies.entities.remote.movies.MoviesListRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Represents the available web services for movies.
 *
 */
interface MoviesService {

  @GET("3/movie/upcoming")
  suspend fun fetchUpcomingMovies(@Query("page") page: Int): Response<MoviesListRemote>

  @GET("3/movie/top_rated")
  suspend fun fetchTopRatedMovies(@Query("page") page: Int): Response<MoviesListRemote>
}
