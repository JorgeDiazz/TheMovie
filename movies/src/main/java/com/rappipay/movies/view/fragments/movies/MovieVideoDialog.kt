package com.rappipay.movies.view.fragments.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.rappipay.components.utils.viewBinding
import com.rappipay.movies.R
import com.rappipay.movies.databinding.DialogMovieVideoBinding

const val MOVIE_VIDEO_DIALOG_TAG = "MOVIE_VIDEO_DIALOG_TAG"

/**
 * Represents the dialog where the video is shown up.
 *
 */
class MovieVideoDialog(private val videoKey: String) : DialogFragment(R.layout.dialog_movie_video) {

  private val binding by viewBinding(DialogMovieVideoBinding::bind)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeView()
  }

  private fun initializeView() {
    initializeYouTubePlayerView()
    playMovieVideo(videoKey)
  }

  private fun initializeYouTubePlayerView() {
    lifecycle.addObserver(binding.youTubePlayerViewMovieVideo)
  }

  private fun playMovieVideo(videoKey: String) {
    binding.youTubePlayerViewMovieVideo.addYouTubePlayerListener(YouTubePlayerView(videoKey))
  }

  inner class YouTubePlayerView(private val videoKey: String) : AbstractYouTubePlayerListener() {
    override fun onReady(youTubePlayer: YouTubePlayer) {
      youTubePlayer.loadVideo(videoKey, 0f)
      youTubePlayer.play()
    }
  }
}
