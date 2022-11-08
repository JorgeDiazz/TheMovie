package com.rappipay.movies.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.core.network.NetworkState
import com.rappipay.movies.entities.local.movies.MovieRoom
import com.rappipay.movies.entities.local.movies.MovieTypeRoom
import com.rappipay.movies.entities.local.remote_keys.RemoteKeysRoom
import com.rappipay.movies.entities.remote.movies.MoviesListRemote
import com.rappipay.movies.entities.remote.movies.toLocalModel
import com.rappipay.movies.exceptions.EndOfPaginationException
import com.rappipay.movies.room.database.MoviesDatabase
import com.rappipay.movies.services.IMoviesRemoteDataSource

/**
 * Represents the paging source of remote movies.
 *
 */
class MoviesPagingSource(private val moviesRemoteDataSource: IMoviesRemoteDataSource, private val moviesDatabase: MoviesDatabase, private val moviesType: MovieTypeRoom) :
  PagingSource<Int, MovieRoom>() {

  companion object {
    const val DEFAULT_PAGE_INDEX = 1
    const val DEFAULT_PAGE_SIZE = 20
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieRoom> {
    val pageIndex = params.key ?: DEFAULT_PAGE_INDEX

    return try {
      val currentKeys = moviesDatabase.getRemoteKeysDao().getMovieRemoteKeysByPage(pageIndex)
      var moviesResponseList = moviesDatabase.getMoviesDao().getMoviesByTypeAndPage(moviesType, pageIndex)

      val endOfPaginationReached: Boolean
      if (currentKeys.isNotEmpty() && moviesResponseList.isNotEmpty()) {
        endOfPaginationReached = pageIndex == currentKeys.first().totalKeys
      } else {
        if (pageIndex == DEFAULT_PAGE_INDEX) {
          clearCache()
        }

        val moviesResponseListResponse: NetworkState.Success<MoviesListRemote?> =
          if (moviesType == MovieTypeRoom.UPCOMING) {
            NetworkState.Success(moviesRemoteDataSource.fetchUpcomingMovies(pageIndex).data)
          } else {
            NetworkState.Success(moviesRemoteDataSource.fetchTopRatedMovies(pageIndex).data)
          }

        val totalPages = moviesResponseListResponse.data?.totalPages ?: -1
        endOfPaginationReached = pageIndex == totalPages

        moviesResponseList = moviesResponseListResponse.data?.results?.toLocalModel(moviesType, pageIndex) ?: emptyList()

        val prevKey = if (pageIndex == DEFAULT_PAGE_INDEX) null else pageIndex - 1
        val nextKey = if (endOfPaginationReached) null else pageIndex + 1

        val keys = moviesResponseList.map {
          RemoteKeysRoom(repoId = it.id, prevKey = prevKey, currKey = pageIndex, nextKey = nextKey, totalKeys = totalPages)
        }

        moviesDatabase.getRemoteKeysDao().insertAll(keys)
        moviesDatabase.getMoviesDao().insertAll(moviesResponseList)
      }

      if (moviesResponseList.isEmpty()) {
        throw EndOfPaginationException()
      }

      LoadResult.Page(
        data = moviesResponseList,
        prevKey = if (pageIndex == DEFAULT_PAGE_INDEX) null else pageIndex - 1,
        nextKey = if (endOfPaginationReached) null else pageIndex + 1
      )
    } catch (exception: Exception) {
      println(exception)
      return LoadResult.Error(exception)
    }
  }

  private suspend fun clearCache() {
    moviesDatabase.getRemoteKeysDao().clearRemoteKeys()
    moviesDatabase.getMoviesDao().clearAllMovies()
  }

  override fun getRefreshKey(state: PagingState<Int, MovieRoom>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }
}
