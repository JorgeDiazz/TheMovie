package com.rappipay.movies.view.fragments.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rappipay.movies.R
import com.rappipay.movies.databinding.CardItemMovieLoaderBinding
import com.rappipay.movies.view.fragments.movies.adapters.MoviesLoaderStateAdapter.LoaderViewHolder

/**
 * Represents the loader state of movies recycler view.
 *
 */
class MoviesLoaderStateAdapter(private val retry: () -> Unit) :
  LoadStateAdapter<LoaderViewHolder>() {

  override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
    holder.bind(loadState)
  }

  override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
    return LoaderViewHolder.getInstance(parent, retry)
  }

  class LoaderViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {
    companion object {
      fun getInstance(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_item_movie_loader, parent, false)
        return LoaderViewHolder(view, retry)
      }
    }

    private val binding = CardItemMovieLoaderBinding.bind(view)

    init {
      binding.buttonRetry.setOnClickListener {
        retry()
      }
    }

    fun bind(loadState: LoadState) {
      if (loadState is LoadState.Loading) {
        binding.motionLayoutLoader.transitionToStart()
      } else {
        binding.motionLayoutLoader.transitionToEnd()
      }
    }
  }
}
