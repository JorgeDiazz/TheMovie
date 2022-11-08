package com.rappipay.movies.view.states

sealed class MovieDetailsState {
  data class Error(val errorMessage: String) : MovieDetailsState()
}
