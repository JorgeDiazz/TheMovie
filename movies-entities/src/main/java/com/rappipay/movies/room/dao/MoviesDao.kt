package com.rappipay.movies.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rappipay.movies.entities.local.movies.MovieRoom
import com.rappipay.movies.entities.local.movies.MovieTypeRoom
import kotlinx.coroutines.flow.Flow

/**
 * Represents Room dao for movie entity.
 *
 */
@Dao
interface MoviesDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertAll(movies: List<MovieRoom>)

  @Query("SELECT * FROM movie WHERE type = :type ")
  fun getMoviesByType(type: MovieTypeRoom): Flow<List<MovieRoom>>

  @Query("SELECT * FROM movie WHERE type = :type AND page = :page")
  suspend fun getMoviesByTypeAndPage(type: MovieTypeRoom, page: Int): List<MovieRoom>

  @Query("SELECT * FROM movie WHERE type = :type AND original_language LIKE :languageIsoCode AND release_date LIKE :releaseYear LIMIT :limit")
  fun getMoviesByTypeAndLanguageIsoCodeAndReleaseYear(type: MovieTypeRoom, languageIsoCode: String, releaseYear: String, limit: Int): Flow<List<MovieRoom>>

  @Query("DELETE FROM movie")
  suspend fun clearAllMovies()
}
