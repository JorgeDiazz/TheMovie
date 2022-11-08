package com.app.core.extensions

import com.app.base.others.DEFAULT_COUNTRY_ISO_CODE
import com.app.base.others.DEFAULT_LANGUAGE_ISO_CODE
import java.util.Locale

class CoreExtensions {

  companion object {
    fun String.getDisplayLanguage(): String =
      Locale(this).getDisplayLanguage(Locale(DEFAULT_LANGUAGE_ISO_CODE, DEFAULT_COUNTRY_ISO_CODE)).replaceFirstChar { it.uppercase() }
  }
}
