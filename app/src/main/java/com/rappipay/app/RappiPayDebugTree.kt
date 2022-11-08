package com.rappipay.app

import timber.log.Timber

/**
 * Represents the debug tree used by the app.
 *
 */
class RappiPayDebugTree : Timber.DebugTree() {
  override fun createStackElementTag(element: StackTraceElement): String {
    return getCleanClassName(newStackTraceElement())
  }

  private fun newStackTraceElement(): StackTraceElement {
    val elements = Throwable().stackTrace
    return elements[9]
  }

  private fun getCleanClassName(element: StackTraceElement): String {
    return String.format("C:%s:%s", element.className, element.lineNumber)
  }
}
