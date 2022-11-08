package com.rappipay.components.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun getCircularProgressImageDrawable(context: Context): Drawable = CircularProgressDrawable(context).apply {
  strokeWidth = 10f
  centerRadius = 30f
  setColorSchemeColors(Color.WHITE)
  start()
}
