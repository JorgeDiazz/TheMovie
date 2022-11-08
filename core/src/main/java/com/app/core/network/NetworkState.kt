package com.app.core.network

sealed class NetworkState<T>(
  val data: T? = null,
  val message: String? = null,
  val exception: Exception? = null,
) {
  class Success<T>(data: T) : NetworkState<T>(data)
  class Error<T>(message: String, exception: Exception?) : NetworkState<T>(null, message, exception)
  class Loading<T> : NetworkState<T>()
}
