package com.rappipay.movies.domain.model

import com.rappipay.movies.entities.local.movies.MovieRoom
import com.rappipay.movies.entities.local.movies.MovieTypeRoom

data class Movie(
  val id: Int,
  val backdropPath: String?,
  val originalTitle: String,
  val title: String,
  val originalLanguage: String,
  val overview: String,
  val popularity: Double,
  val posterPath: String?,
  val voteAverage: Double,
  val releaseDate: String,
  val type: MovieType,
)

enum class MovieType {
  UPCOMING, TOP_RATED
}

fun MovieRoom.toBaseModel(): Movie =
  Movie(id, backdropPath, originalTitle, title, originalLanguage, overview, popularity, posterPath, voteAverage, releaseDate, if (type == MovieTypeRoom.UPCOMING) MovieType.UPCOMING else MovieType.TOP_RATED)
