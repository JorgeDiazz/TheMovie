package com.rappipay.movies.entities.local.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieRoom(
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "internal_id") val internal_id: Int = 0,
  @ColumnInfo(name = "id") val id: Int,
  @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
  @ColumnInfo(name = "original_title") val originalTitle: String,
  @ColumnInfo(name = "title") val title: String,
  @ColumnInfo(name = "original_language") val originalLanguage: String,
  @ColumnInfo(name = "overview") val overview: String,
  @ColumnInfo(name = "popularity") val popularity: Double,
  @ColumnInfo(name = "poster_path") val posterPath: String?,
  @ColumnInfo(name = "vote_average") val voteAverage: Double,
  @ColumnInfo(name = "release_date") val releaseDate: String,
  @ColumnInfo(name = "type") val type: MovieTypeRoom,
  @ColumnInfo(name = "page") val page: Int,
)

enum class MovieTypeRoom {
  UPCOMING, TOP_RATED
}
