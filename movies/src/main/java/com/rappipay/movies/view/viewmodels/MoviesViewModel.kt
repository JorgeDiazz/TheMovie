package com.rappipay.movies.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.app.base.interfaces.FlowUseCase
import com.app.base.others.DEFAULT_DATE_DELIMITER
import com.app.core.interfaces.AppResources
import com.rappipay.movies.R
import com.rappipay.movies.domain.model.Movie
import com.rappipay.movies.domain.model.MovieLanguage
import com.rappipay.movies.domain.model.MoviesFilters
import com.rappipay.movies.qualifiers.GetAvailableMoviesFilters
import com.rappipay.movies.qualifiers.GetSuggestedMovies
import com.rappipay.movies.qualifiers.GetTopRatedMovies
import com.rappipay.movies.qualifiers.GetUpcomingMovies
import com.rappipay.movies.view.uimodel.MovieUiModel
import com.rappipay.movies.view.uimodel.MoviesFiltersUiModel
import com.rappipay.movies.view.utils.SPINNER_TITLE_INDEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the ViewModel layer of MoviesFragment.
 *
 */
@HiltViewModel
class MoviesViewModel @Inject constructor(
  private val appResources: AppResources,
  @GetUpcomingMovies private val getUpcomingMoviesUseCase: FlowUseCase<Unit, PagingData<Movie>>,
  @GetTopRatedMovies private val getTopRatedMoviesUseCase: FlowUseCase<Unit, PagingData<Movie>>,
  @GetAvailableMoviesFilters private val getAvailableMoviesFiltersUseCase: FlowUseCase<Unit, MoviesFilters>,
  @GetSuggestedMovies private val getSuggestedMoviesUseCase: FlowUseCase<Pair<String?, Int?>, List<Movie>>,
) : ViewModel() {

  private val _upcomingMoviesPagingStateFlow = MutableStateFlow<PagingData<MovieUiModel>>(PagingData.empty())
  val upcomingMoviesPagingStateFlow: StateFlow<PagingData<MovieUiModel>> = _upcomingMoviesPagingStateFlow

  private val _topRatedMoviesPagingStateFlow = MutableStateFlow<PagingData<MovieUiModel>>(PagingData.empty())
  val topRatedMoviesPagingStateFlow: StateFlow<PagingData<MovieUiModel>> = _topRatedMoviesPagingStateFlow

  private val _suggestedMoviesPagingStateFlow = MutableStateFlow<List<MovieUiModel>>(emptyList())
  val suggestedMoviesPagingStateFlow: StateFlow<List<MovieUiModel>> = _suggestedMoviesPagingStateFlow

  private val _moviesFiltersStateFlow = MutableStateFlow(MoviesFiltersUiModel())
  val moviesFiltersStateFlow: StateFlow<MoviesFiltersUiModel> = _moviesFiltersStateFlow

  private var suggestedMoviesJob: Job? = null

  private var currentLanguageIndex: Int = -1
  private var currentReleaseYearIndex: Int = -1

  fun onViewActive() {
    loadUpcomingMovies()
    loadAvailableMoviesFilters()
    loadTopRatedMovies()
    loadSuggestedMovies()
  }

  private fun loadUpcomingMovies() = viewModelScope.launch {
    getUpcomingMoviesUseCase.execute(Unit)
      .cachedIn(viewModelScope)
      .map { it.toUiModel() }
      .collectLatest {
        _upcomingMoviesPagingStateFlow.value = it
      }
  }

  private fun loadTopRatedMovies() = viewModelScope.launch {
    getTopRatedMoviesUseCase.execute(Unit)
      .cachedIn(viewModelScope)
      .map { it.toUiModel() }
      .collectLatest {
        _topRatedMoviesPagingStateFlow.value = it
      }
  }

  fun loadSuggestedMovies(languageIndex: Int = SPINNER_TITLE_INDEX, releaseYearIndex: Int = SPINNER_TITLE_INDEX) {
    suggestedMoviesJob?.cancel()

    suggestedMoviesJob = viewModelScope.launch {
      currentLanguageIndex = if (languageIndex == -1) currentLanguageIndex else languageIndex
      currentReleaseYearIndex = if (releaseYearIndex == -1) currentReleaseYearIndex else releaseYearIndex

      val languageIsoCode = _moviesFiltersStateFlow.value.languagesList.getOrNull(currentLanguageIndex)?.languageIsoCode
      val releaseYear = _moviesFiltersStateFlow.value.releaseYearsList.getOrNull(currentReleaseYearIndex)?.toIntOrNull()

      getSuggestedMoviesUseCase.execute(languageIsoCode to releaseYear)
        .map { it.toUiModel() }
        .collectLatest {
          _suggestedMoviesPagingStateFlow.value = it
        }
    }
  }

  private fun loadAvailableMoviesFilters() = viewModelScope.launch {
    getAvailableMoviesFiltersUseCase.execute(Unit)
      .map { it.toUiModel() }
      .collectLatest {
        _moviesFiltersStateFlow.value = it
      }
  }

  private fun List<Movie>.toUiModel(): List<MovieUiModel> = map { it.toUiModel() }

  private fun PagingData<Movie>.toUiModel(): PagingData<MovieUiModel> = map { it.toUiModel() }

  private fun Movie.toUiModel(): MovieUiModel =
    MovieUiModel(id, backdropPath, originalTitle, title, originalLanguage, overview, popularity, posterPath, voteAverage, releaseDate.substringBefore(DEFAULT_DATE_DELIMITER).toInt(), type)

  private fun MoviesFilters.toUiModel(): MoviesFiltersUiModel =
    MoviesFiltersUiModel(
      listOf(MovieLanguage(languageName = appResources.getString(R.string.language))) + languagesList,
      listOf(appResources.getString(R.string.release_year)) + releaseYearsList.map { it.toString() }
    )
}
