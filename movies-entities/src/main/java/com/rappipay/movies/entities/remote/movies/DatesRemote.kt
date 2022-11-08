package com.rappipay.movies.entities.remote.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DatesRemote(
  @Json(name = "maximum")
  val maximum: String,
  @Json(name = "minimum")
  val minimum: String
)
