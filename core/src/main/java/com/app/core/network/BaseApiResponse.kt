package com.app.core.network

import retrofit2.Response

abstract class BaseApiResponse {
  suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkState<T> {
    try {
      val response = apiCall()
      if (response.isSuccessful) {
        val body = response.body()
        body?.let {
          return NetworkState.Success(body)
        }
      }

      throw Exception("${response.code()} ${response.message()}")
    } catch (exception: Exception) {
      return error(exception.message ?: exception.toString(), exception)
    }
  }

  private fun <T> error(errorMessage: String, exception: Exception): NetworkState<T> =
    NetworkState.Error("Api call failed $errorMessage", exception)
}
