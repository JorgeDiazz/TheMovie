package com.rappipay.movies.view.fragments.movies

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.base.interfaces.Logger
import com.rappipay.components.utils.viewBinding
import com.rappipay.movies.R
import com.rappipay.movies.databinding.FragmentMoviesBinding
import com.rappipay.movies.view.fragments.movies.adapters.MoviesAdapter
import com.rappipay.movies.view.fragments.movies.adapters.MoviesLoaderStateAdapter
import com.rappipay.movies.view.uimodel.MovieUiModel
import com.rappipay.movies.view.uimodel.MoviesFiltersUiModel
import com.rappipay.movies.view.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * Represents the list of movies pulled in from TMDB API.
 *
 */
@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies), MoviesAdapter.OnClickListener {

  @Inject
  lateinit var logger: Logger

  private val viewModel by viewModels<MoviesViewModel>()
  private val binding by viewBinding(FragmentMoviesBinding::bind)

  private lateinit var upcomingMoviesRecyclerView: RecyclerView
  private lateinit var upcomingMoviesAdapter: MoviesAdapter

  private lateinit var topRatedMoviesRecyclerView: RecyclerView
  private lateinit var topRatedMoviesAdapter: MoviesAdapter

  private lateinit var suggestedMoviesRecyclerView: RecyclerView
  private lateinit var suggestedMoviesAdapter: MoviesAdapter

  private lateinit var moviesLanguageSpinner: Spinner
  private lateinit var moviesLanguageArrayAdapter: ArrayAdapter<String>

  private lateinit var moviesReleaseYearSpinner: Spinner
  private lateinit var moviesReleaseYearArrayAdapter: ArrayAdapter<String>

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeView()
    initializeViewModel()
    initializeObservers()
  }

  private fun initializeView() {
    setUpRecyclerViewUpcomingMovies()
    setUpRecyclerViewTopRatedMovies()
    setUpRecyclerViewSuggestedMovies()
    setUpMoviesLanguageSpinner()
    setUpMoviesReleaseYearSpinner()
  }

  private fun setUpRecyclerViewUpcomingMovies() {
    upcomingMoviesAdapter = MoviesAdapter(requireContext(), this, false)

    upcomingMoviesRecyclerView = binding.recyclerViewUpcomingMovies
    upcomingMoviesRecyclerView.apply {
      layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
      adapter = upcomingMoviesAdapter.withLoadStateFooter(MoviesLoaderStateAdapter { upcomingMoviesAdapter.retry() })
    }
  }

  private fun setUpRecyclerViewTopRatedMovies() {
    topRatedMoviesAdapter = MoviesAdapter(requireContext(), this, false)

    topRatedMoviesRecyclerView = binding.recyclerViewTopRatedMovies
    topRatedMoviesRecyclerView.apply {
      layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
      adapter = topRatedMoviesAdapter.withLoadStateFooter(MoviesLoaderStateAdapter { topRatedMoviesAdapter.retry() })
    }
  }

  private fun setUpRecyclerViewSuggestedMovies() {
    suggestedMoviesAdapter = MoviesAdapter(requireContext(), this, true)

    suggestedMoviesRecyclerView = binding.recyclerViewSuggestedMovies
    suggestedMoviesRecyclerView.apply {
      layoutManager = GridLayoutManager(context, 2)
      adapter = suggestedMoviesAdapter.withLoadStateFooter(MoviesLoaderStateAdapter { suggestedMoviesAdapter.retry() })
    }
  }

  private fun setUpMoviesLanguageSpinner() {
    moviesLanguageArrayAdapter = ArrayAdapter<String>(requireContext(), R.layout.item_filters_spinner)
    moviesLanguageSpinner = binding.spinnerMoviesLanguage.apply {
      adapter = moviesLanguageArrayAdapter
      onItemSelectedListener = MoviesLanguageOnClickListener()
    }
  }

  private fun setUpMoviesReleaseYearSpinner() {
    moviesReleaseYearArrayAdapter = ArrayAdapter<String>(requireContext(), R.layout.item_filters_spinner)
    moviesReleaseYearSpinner = binding.spinnerMoviesReleaseYear.apply {
      adapter = moviesReleaseYearArrayAdapter
      onItemSelectedListener = MoviesReleaseYearOnClickListener()
    }
  }

  private fun initializeViewModel() {
    viewModel.onViewActive()
  }

  private fun initializeObservers() {
    initializeUpcomingMoviesObserver()
    initializeTopRatedMoviesObserver()
    initializeMoviesFiltersObserver()
    initializeSuggestedMoviesObserver()
  }

  private fun initializeUpcomingMoviesObserver() {
    lifecycleScope.launch {
      viewModel.upcomingMoviesPagingStateFlow.flowWithLifecycle(lifecycle).collect { upcomingMoviesPagingData ->
        observeUpcomingMoviesPagingData(upcomingMoviesPagingData)
      }
    }
  }

  private fun initializeTopRatedMoviesObserver() {
    lifecycleScope.launch {
      viewModel.topRatedMoviesPagingStateFlow.flowWithLifecycle(lifecycle).collect { topRatedMoviesPagingData ->
        observeTopRatedMoviesPagingData(topRatedMoviesPagingData)
      }
    }
  }

  private fun initializeMoviesFiltersObserver() {
    lifecycleScope.launch {
      viewModel.moviesFiltersStateFlow.flowWithLifecycle(lifecycle).collect { moviesFiltersUiModel ->
        observeMoviesFilters(moviesFiltersUiModel)
      }
    }
  }

  private fun initializeSuggestedMoviesObserver() {
    lifecycleScope.launch {
      viewModel.suggestedMoviesPagingStateFlow.flowWithLifecycle(lifecycle).collect { suggestedMoviesPagingData ->
        observeSuggestedMoviesList(suggestedMoviesPagingData)
      }
    }
  }

  private fun updateSuggestedMoviesObserver(languageIndex: Int = -1, releaseYearIndex: Int = -1) {
    viewModel.loadSuggestedMovies(languageIndex = languageIndex, releaseYearIndex = releaseYearIndex)
  }

  private suspend fun observeUpcomingMoviesPagingData(upcomingMoviesPagingData: PagingData<MovieUiModel>) {
    upcomingMoviesAdapter.submitData(upcomingMoviesPagingData)
  }

  private suspend fun observeTopRatedMoviesPagingData(topRatedMoviesPagingData: PagingData<MovieUiModel>) {
    topRatedMoviesAdapter.submitData(topRatedMoviesPagingData)
  }

  private suspend fun observeSuggestedMoviesList(suggestedMoviesList: List<MovieUiModel>) {
    suggestedMoviesAdapter.submitData(PagingData.from(suggestedMoviesList))
  }

  private fun observeMoviesFilters(moviesFiltersUiModel: MoviesFiltersUiModel) {
    moviesFiltersUiModel.run {
      updateMoviesLanguageArrayAdapterContent(languagesList.map { it.languageName }, languageSelectedItem)
      updateMoviesReleaseYearArrayAdapterContent(releaseYearsList, releaseYearSelectedItem)
    }
  }

  private fun updateMoviesLanguageArrayAdapterContent(languagesList: List<String>, languageSelectedItem: String) {
    moviesLanguageArrayAdapter.clear()
    moviesLanguageArrayAdapter.addAll(languagesList)

    val itemSelectedIndex = if (languageSelectedItem.isEmpty()) 0 else languagesList.indexOf(languageSelectedItem)
    moviesLanguageSpinner.setSelection(itemSelectedIndex)
  }

  private fun updateMoviesReleaseYearArrayAdapterContent(releaseYearsList: List<String>, releaseYearSelectedItem: String) {
    moviesReleaseYearArrayAdapter.clear()
    moviesReleaseYearArrayAdapter.addAll(releaseYearsList)

    val itemSelectedIndex = if (releaseYearSelectedItem.isEmpty()) 0 else releaseYearsList.indexOf(releaseYearSelectedItem)
    moviesReleaseYearSpinner.setSelection(itemSelectedIndex)
  }

  override fun onMovieClick(movieUiModel: MovieUiModel) {
    navigateToMovieDetailsFragment(movieUiModel)
  }

  private fun navigateToMovieDetailsFragment(movieUiModel: MovieUiModel) {
    val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(movieUiModel)
    findNavController().navigate(action)
  }

  inner class MoviesLanguageOnClickListener : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      updateSuggestedMoviesObserver(languageIndex = position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
      // no-op by default
    }
  }

  inner class MoviesReleaseYearOnClickListener : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      updateSuggestedMoviesObserver(releaseYearIndex = position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
      // no-op by default
    }
  }
}
