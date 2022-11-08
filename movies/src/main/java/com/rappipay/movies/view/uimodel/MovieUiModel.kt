package com.rappipay.movies.view.uimodel

import android.os.Parcelable
import com.rappipay.movies.domain.model.MovieType
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUiModel(
  val id: Int,
  val backdropPath: String?,
  val originalTitle: String,
  val title: String,
  val originalLanguage: String,
  val overview: String,
  val popularity: Double,
  val posterPath: String?,
  val voteAverage: Double,
  val type: MovieType,
) : Parcelable
