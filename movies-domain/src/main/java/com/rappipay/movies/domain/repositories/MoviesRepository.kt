package com.rappipay.movies.domain.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.base.others.DEFAULT_COUNTRY_ISO_CODE
import com.app.base.others.DEFAULT_DATE_DELIMITER
import com.app.base.others.DEFAULT_LANGUAGE_ISO_CODE
import com.app.core.network.BaseApiResponse
import com.rappipay.movies.domain.model.Movie
import com.rappipay.movies.domain.model.MovieLanguage
import com.rappipay.movies.domain.model.MovieVideoData
import com.rappipay.movies.domain.model.MoviesFilters
import com.rappipay.movies.domain.model.toBaseModel
import com.rappipay.movies.entities.local.movies.MovieTypeRoom
import com.rappipay.movies.exceptions.NoMovieVideosFound
import com.rappipay.movies.repositories.MoviesPagingSource
import com.rappipay.movies.room.database.MoviesDatabase
import com.rappipay.movies.services.IMoviesRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Locale

const val SUGGESTED_MOVIES_DEFAULT_LIMIT = 6

/**
 * Represents the repository for movies.
 *
 */
class MoviesRepository @Inject constructor(
  private val moviesRemoteDataSource: IMoviesRemoteDataSource,
  private val moviesDatabase: MoviesDatabase,
) : BaseApiResponse(), IMoviesRepository {

  override fun getUpcomingMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>> {
    return Pager(
      config = pagingConfig,
      pagingSourceFactory = { MoviesPagingSource(moviesRemoteDataSource, moviesDatabase, MovieTypeRoom.UPCOMING) }
    ).flow.map {
      it.map { movieRoom -> movieRoom.toBaseModel() }
    }
  }

  override fun getTopRatedMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>> {
    return Pager(
      config = pagingConfig,
      pagingSourceFactory = { MoviesPagingSource(moviesRemoteDataSource, moviesDatabase, MovieTypeRoom.TOP_RATED) }
    ).flow.map {
      it.map { movieRoom -> movieRoom.toBaseModel() }
    }
  }

  override fun getAvailableMoviesFilters(): Flow<MoviesFilters> {
    return moviesDatabase.getMoviesDao().getMoviesByType(MovieTypeRoom.TOP_RATED).map { topRatedMovies ->
      val languagesSet = mutableSetOf<MovieLanguage>()
      val releaseYearsSet = mutableSetOf<Int>()

      topRatedMovies.forEach { movieRoom ->
        val languageIsoCode = movieRoom.originalLanguage
        val languageName = Locale(languageIsoCode).getDisplayLanguage(Locale(DEFAULT_LANGUAGE_ISO_CODE, DEFAULT_COUNTRY_ISO_CODE)).replaceFirstChar { it.uppercase() }
        val movieLanguage = MovieLanguage(languageIsoCode, languageName)

        languagesSet.add(movieLanguage)

        val releaseYear = movieRoom.releaseDate.substringBefore(DEFAULT_DATE_DELIMITER).toInt()
        releaseYearsSet.add(releaseYear)
      }

      val moviesFilters = MoviesFilters(languagesSet.toList().sortedBy { it.languageName }, releaseYearsSet.toList().sorted())

      moviesFilters
    }
  }

  override fun getSuggestedMoviesAvailableMoviesFilters(languageIsoCode: String?, releaseYear: Int?): Flow<List<Movie>> {
    val languageIsoCodeQuery = languageIsoCode ?: "%"
    val releaseYearQuery = if (releaseYear == null) "%" else "%$releaseYear%"

    return moviesDatabase.getMoviesDao().getMoviesByTypeAndLanguageIsoCodeAndReleaseYear(
      MovieTypeRoom.TOP_RATED, languageIsoCodeQuery, releaseYearQuery, SUGGESTED_MOVIES_DEFAULT_LIMIT
    ).map { suggestedMovies -> suggestedMovies.map { it.toBaseModel() } }
  }

  override fun getMovieVideoData(movieId: Int): Flow<MovieVideoData> = flow {
    val movieVideosDataListRemote = moviesRemoteDataSource.fetchMovieVideosData(movieId).data
    val movieVideosData = movieVideosDataListRemote?.results?.firstOrNull()?.toBaseModel() ?: throw NoMovieVideosFound()
    emit(movieVideosData)
  }
}
