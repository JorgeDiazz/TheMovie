package com.app.core.data

import android.content.Context
import android.content.SharedPreferences
import com.app.base.interfaces.Cache

class SharedPreferencesCache(name: String, context: Context) : Cache {

  private var sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
  private val editor: SharedPreferences.Editor = sharedPreferences.edit()

  override fun saveBoolean(key: String, value: Boolean) {
    editor.putBoolean(key, value)
    editor.commit()
  }

  override fun readBoolean(key: String, default: Boolean): Boolean {
    return sharedPreferences.getBoolean(key, default)
  }

  override fun removeValue(key: String) {
    editor.remove(key)
    editor.apply()
  }

  override fun containsValues(): Boolean {
    return sharedPreferences.all.isNotEmpty()
  }

  override fun clearAll() {
    editor.clear()
    editor.apply()
  }
}
