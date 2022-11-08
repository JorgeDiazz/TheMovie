package com.rappipay.movies.domain.model

data class MoviesFilters(
  val languagesList: List<MovieLanguage> = emptyList(),
  val releaseYearsList: List<Int> = emptyList()
)
