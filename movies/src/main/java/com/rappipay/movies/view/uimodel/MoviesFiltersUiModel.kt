package com.rappipay.movies.view.uimodel

import com.rappipay.movies.domain.model.MovieLanguage

data class MoviesFiltersUiModel(
  val languagesList: List<MovieLanguage> = emptyList(),
  val languageSelectedItem: String = "",
  val releaseYearsList: List<String> = emptyList(),
  val releaseYearSelectedItem: String = "",
)
