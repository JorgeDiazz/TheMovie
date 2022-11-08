package com.rappipay.movies.view.fragments.movies.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.app.base.others.POSTER_IMAGES_BASE_URL
import com.rappipay.movies.R
import com.rappipay.movies.databinding.CardItemMovieNormalSizeBinding
import com.rappipay.movies.view.uimodel.MovieUiModel

/**
 * Represents the adapter of several movies recycler view.
 *
 */
class MoviesAdapter(private val context: Context, private val onClickListener: OnClickListener, private val gridLayout: Boolean) :
  PagingDataAdapter<MovieUiModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

  companion object {
    private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MovieUiModel>() {
      override fun areItemsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean =
        oldItem == newItem

      override fun areContentsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean =
        oldItem == newItem
    }
  }

  interface OnClickListener {
    fun onMovieClick(movieUiModel: MovieUiModel)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as MovieViewHolder).bind(getItem(position))
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return MovieViewHolder.getInstance(parent, context, onClickListener, gridLayout)
  }

  class MovieViewHolder(view: View, private val context: Context, private val onClickListener: OnClickListener) : RecyclerView.ViewHolder(view) {

    companion object {
      fun getInstance(parent: ViewGroup, context: Context, onClickListener: OnClickListener, gridLayout: Boolean): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = if (gridLayout) R.layout.card_item_movie_large_size else R.layout.card_item_movie_normal_size
        val view = inflater.inflate(layout, parent, false)

        return MovieViewHolder(view, context, onClickListener)
      }
    }

    private val binding = CardItemMovieNormalSizeBinding.bind(view)

    fun bind(movieUiModel: MovieUiModel?) {
      movieUiModel?.let {
        binding.imageViewMovie.load(POSTER_IMAGES_BASE_URL + movieUiModel.posterPath) {
          crossfade(true)
          error(R.drawable.ic_no_image_found)
          placeholder(getCircularProgressImageDrawable())
        }
      }
    }

    private fun getCircularProgressImageDrawable() = CircularProgressDrawable(context).apply {
      strokeWidth = 10f
      centerRadius = 30f
      setColorSchemeColors(Color.WHITE)
      start()
    }
  }
}
