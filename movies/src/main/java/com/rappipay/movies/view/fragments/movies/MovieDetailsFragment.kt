package com.rappipay.movies.view.fragments.movies

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.ImageLoader
import coil.load
import coil.size.Scale
import com.app.base.interfaces.Logger
import com.app.base.others.POSTER_IMAGES_BASE_URL
import com.rappipay.components.utils.getCircularProgressImageDrawable
import com.rappipay.components.utils.viewBinding
import com.rappipay.movies.R
import com.rappipay.movies.databinding.FragmentMovieDetailsBinding
import com.rappipay.movies.view.uimodel.MovieUiModel
import com.rappipay.movies.view.uimodel.MovieVideoDataUiModel
import com.rappipay.movies.view.viewmodels.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * Represents the detailed information of a given movie.
 *
 */
@AndroidEntryPoint
class MovieDetailsFragment : DialogFragment(R.layout.fragment_movie_details) {

  @Inject
  lateinit var logger: Logger

  private val viewModel by viewModels<MovieDetailsViewModel>()
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

    initializeView()
    initializeObservers()
  }

  private fun initializeView() {
    initializeToolbar()
    initializeMoviePosterImage()
    initializeMovieTitle()
    initializeMovieOverview()
    initializeWatchTrailerButton()
  }

  private fun initializeToolbar() {
    binding.toolbarMovieDetails.apply {
      (requireActivity() as AppCompatActivity).setSupportActionBar(this.toolbar)
      setBackButton(true)
      setBackButtonListener { popFragment() }
    }
  }

  private fun initializeMovieTitle() {
    binding.textViewMovieTitle.text = movieUiModel.title
  }

  private fun initializeMovieOverview() {
    binding.textViewOverview.text = movieUiModel.overview
  }

  private fun initializeMoviePosterImage() {
    val imageLoader = ImageLoader.Builder(requireContext()).diskCache(null).memoryCache(null).build()

    binding.imageViewMovie.load(POSTER_IMAGES_BASE_URL + movieUiModel.posterPath, imageLoader) {
      crossfade(true)
      error(R.drawable.ic_no_image_found)
      placeholder(getCircularProgressImageDrawable(requireContext()))
    }
  }

  private fun initializeWatchTrailerButton() {
    binding.buttonWatchTrailer.setOnClickListener {
      viewModel.loadMovieVideoData(movieUiModel.id)
    }
  }

  private fun initializeObservers() {
    initializeMovieVideoDataObserver()
  }

  private fun initializeMovieVideoDataObserver() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.movieVideoDataSharedFlow.collect { movieVideoDataUiModel ->
          observeMovieVideoData(movieVideoDataUiModel)
        }
      }
    }
  }

  private fun observeMovieVideoData(movieVideoDataUiModel: MovieVideoDataUiModel) {
    movieVideoDataUiModel.videoKey?.let {
      showMovieVideoDialog(it)
    }
  }

  private fun showMovieVideoDialog(videoKey: String) {
    (childFragmentManager.findFragmentByTag(MOVIE_VIDEO_DIALOG_TAG) as? DialogFragment)?.dismiss()
    MovieVideoDialog(videoKey).show(childFragmentManager, MOVIE_VIDEO_DIALOG_TAG)
  }

  private fun popFragment() = requireActivity().onBackPressed()
}
