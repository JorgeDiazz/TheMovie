package com.rappipay.movies.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rappipay.movies.entities.local.movies.MovieRoom
import com.rappipay.movies.entities.local.remote_keys.RemoteKeysRoom
import com.rappipay.movies.room.dao.MoviesDao
import com.rappipay.movies.room.dao.RemoteKeysDao

@Database(version = 1, entities = [MovieRoom::class, RemoteKeysRoom::class], exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

  abstract fun getRemoteKeysDao(): RemoteKeysDao
  abstract fun getMoviesDao(): MoviesDao
}
