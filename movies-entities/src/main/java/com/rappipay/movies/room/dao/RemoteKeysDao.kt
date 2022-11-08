package com.rappipay.movies.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rappipay.movies.entities.local.remote_keys.RemoteKeysRoom

/**
 * Represents Room dao for remote keys entities.
 *
 */
@Dao
interface RemoteKeysDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertAll(remoteKey: List<RemoteKeysRoom>)

  @Query("SELECT * FROM remote_keys WHERE curr_key = :currentKey")
  suspend fun getMovieRemoteKeysByPage(currentKey: Int): List<RemoteKeysRoom>

  @Query("DELETE FROM remote_keys")
  suspend fun clearRemoteKeys()
}
