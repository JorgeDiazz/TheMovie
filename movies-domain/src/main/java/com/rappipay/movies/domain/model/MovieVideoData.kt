package com.rappipay.movies.domain.model

import com.rappipay.movies.entities.remote.movies.videos.MovieVideosDataRemote

data class MovieVideoData(
  val key: String,
  val site: String,
)

fun MovieVideosDataRemote.toBaseModel(): MovieVideoData = MovieVideoData(key, site)