package com.rappipay.movies.view.fragments.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.app.base.interfaces.Logger
import com.rappipay.components.utils.viewBinding
import com.rappipay.movies.R
import com.rappipay.movies.databinding.FragmentMovieDetailsBinding
import com.rappipay.movies.view.uimodel.MovieUiModel
import com.rappipay.movies.view.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Represents the detailed information of a given movie.
 *
 */
@AndroidEntryPoint
class MovieDetailsFragment : DialogFragment(R.layout.fragment_movie_details) {

  @Inject
  lateinit var logger: Logger

  private val moviesViewModel by activityViewModels<MoviesViewModel>()
  private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

  private val movieUiModel: MovieUiModel by lazy {
    MovieDetailsFragmentArgs.fromBundle(requireArguments()).movieUiModel
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}
