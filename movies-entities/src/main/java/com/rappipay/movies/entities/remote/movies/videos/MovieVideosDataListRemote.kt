package com.rappipay.movies.entities.remote.movies.videos


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieVideosDataListRemote(
    @Json(name = "id")
    val id: Int,
    @Json(name = "results")
    val results: List<MovieVideosDataRemote>
)