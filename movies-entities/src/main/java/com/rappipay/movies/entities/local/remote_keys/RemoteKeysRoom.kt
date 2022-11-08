package com.rappipay.movies.entities.local.remote_keys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysRoom(
  @PrimaryKey @ColumnInfo(name = "repo_id") val repoId: Int,
  @ColumnInfo(name = "prev_key") val prevKey: Int?,
  @ColumnInfo(name = "curr_key") val currKey: Int?,
  @ColumnInfo(name = "next_key") val nextKey: Int?,
  @ColumnInfo(name = "total_keys") val totalKeys: Int?,
)
