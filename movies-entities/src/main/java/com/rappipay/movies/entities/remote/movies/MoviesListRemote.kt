package com.rappipay.movies.entities.remote.movies

import com.rappipay.movies.entities.local.movies.MovieRoom
import com.rappipay.movies.entities.local.movies.MovieTypeRoom
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesListRemote(
  @Json(name = "dates")
  val dates: DatesRemote?,
  @Json(name = "page")
  val page: Int,
  @Json(name = "results")
  val results: List<MovieRemote>,
  @Json(name = "total_pages")
  val totalPages: Int,
  @Json(name = "total_results")
  val totalResults: Int
)

fun List<MovieRemote>.toLocalModel(type: MovieTypeRoom, page: Int): List<MovieRoom> = map { it.toLocalModel(type, page) }
fun MovieRemote.toLocalModel(type: MovieTypeRoom, page: Int): MovieRoom =
  MovieRoom(
    id = id, backdropPath = backdropPath, originalTitle = originalTitle, title = title, originalLanguage = originalLanguage,
    overview = overview, popularity = popularity, posterPath = posterPath, voteAverage = voteAverage, releaseDate = releaseDate, type = type, page = page
  )
