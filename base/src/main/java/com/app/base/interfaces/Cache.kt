package com.app.base.interfaces

interface Cache {
  fun saveBoolean(key: String, value: Boolean)

  fun readBoolean(key: String, default: Boolean): Boolean

  fun removeValue(key: String)

  fun containsValues(): Boolean

  fun clearAll()
}
