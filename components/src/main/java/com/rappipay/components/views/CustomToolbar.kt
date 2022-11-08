package com.rappipay.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.rappipay.components.R
import com.rappipay.components.databinding.CustomToolbarBinding

class CustomToolbar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

  val toolbar: Toolbar
  var toolbarMenuId = -1

  private val binding: CustomToolbarBinding
  private val padding = resources.getDimensionPixelSize(R.dimen.spacing_normal_600)

  init {
    val layoutParams =
      MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    setLayoutParams(layoutParams)
    binding = CustomToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    toolbar = binding.toolbarCustom

    setBackgroundResource(R.color.white)

    val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
    val showBackButton = attributes.getBoolean(R.styleable.CustomToolbar_showBackButton, false)
    setBackButton(showBackButton)

    styleToolbar()

    attributes.recycle()
  }

  private fun setPaddingForToolbar() {
    when {
      toolbarMenuId != -1 && toolbar.navigationIcon != null -> toolbar.setPadding(0, 0, 0, 0)
      toolbar.navigationIcon != null -> toolbar.setPadding(0, 0, padding, 0)
      toolbarMenuId != -1 -> toolbar.setPadding(padding, 0, 0, 0)
      else -> toolbar.setPadding(padding, 0, padding, 0)
    }
  }

  private fun styleToolbar() {
    setPaddingForToolbar()
    setBackgroundResource(R.color.white)

    toolbar.navigationIcon?.setTint(ContextCompat.getColor(context, R.color.white))
    toolbar.title = ""

    invalidate()
    requestLayout()
  }

  fun setBackButton(show: Boolean) {
    setPaddingForToolbar()

    toolbar.navigationIcon =
      if (show) ContextCompat.getDrawable(context, R.drawable.ic_arrow_back) else null

    toolbar.navigationIcon?.setTint(
      ContextCompat.getColor(
        context,
        android.R.color.white
      )
    )
  }

  fun setBackButtonListener(listener: () -> Unit) {
    toolbar.apply {
      setNavigationOnClickListener {
        listener.invoke()
      }
    }
  }
}
