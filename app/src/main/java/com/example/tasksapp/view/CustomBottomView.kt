package com.example.tasksapp.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.tasksapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomBottomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    init {
        setupCustomView()
    }

    private fun setupCustomView() {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        )

        val colors = intArrayOf(
            ContextCompat.getColor(context, R.color.purple_deep),
            ContextCompat.getColor(context, R.color.gray_text)
        )

        val colorStateList = ColorStateList(states, colors)
        itemIconTintList = colorStateList
        itemTextColor = colorStateList
    }
}
