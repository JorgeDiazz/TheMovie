package com.rappipay.movies.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.base.interfaces.FlowUseCase
import com.app.base.interfaces.Logger
import com.app.base.others.YOUTUBE
import com.app.core.interfaces.AppResources
import com.rappipay.movies.R
import com.rappipay.movies.domain.model.MovieVideoData
import com.rappipay.movies.qualifiers.GetMovieVideoData
import com.rappipay.movies.view.states.MovieDetailsState
import com.rappipay.movies.view.uimodel.MovieVideoDataUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Represents the ViewModel layer of MoviesDetailFragment.
 *
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  private val resources: AppResources,
  private val logger: Logger,
  @GetMovieVideoData private val getMovieVideoDataUseCase: FlowUseCase<Int, MovieVideoData>
) : ViewModel() {

  private val _movieVideoDataSharedFlow = MutableSharedFlow<MovieVideoDataUiModel>()
  val movieVideoDataSharedFlow: SharedFlow<MovieVideoDataUiModel> = _movieVideoDataSharedFlow

  private val _newsSharedFlow = MutableSharedFlow<MovieDetailsState>()
  val newsSharedFlow: SharedFlow<MovieDetailsState> = _newsSharedFlow

  fun loadMovieVideoData(movieId: Int) = viewModelScope.launch {
    getMovieVideoDataUseCase.execute(movieId)
      .map { it.toUiModel() }
      .catch { exception ->
        _newsSharedFlow.emit(MovieDetailsState.Error(resources.getString(R.string.try_again_error)))
        logger.e("An exception has occurred in MovieDetailsViewModel!", exception)
      }
      .collectLatest {
        _movieVideoDataSharedFlow.emit(it)
      }
  }

  private fun MovieVideoData.toUiModel(): MovieVideoDataUiModel =
    MovieVideoDataUiModel(if (site.equals(YOUTUBE, ignoreCase = true)) key else null)
}
