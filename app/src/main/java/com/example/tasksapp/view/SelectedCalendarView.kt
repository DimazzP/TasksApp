package com.example.tasksapp.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.tasksapp.R

class SelectedCalendarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    private val selectedDays = mutableSetOf<Int>()
    private val customFont: Typeface? = ResourcesCompat.getFont(context, R.font.inter_regular)

    init {
        rowCount = 6
        columnCount = 7
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        createCalendar()
    }

    private fun createCalendar() {
        for (day in 1..31) {
            val dayTextView = TextView(context).apply {
                text = day.toString()
                textSize = 14f
                textAlignment = TEXT_ALIGNMENT_CENTER
                setPadding(14, 14, 14, 14)
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
                background = ContextCompat.getDrawable(context, R.drawable.normal_day_shape)
                setOnClickListener { onDayClicked(day, this) }
                typeface = customFont
                layoutParams = LayoutParams().apply {
                    width = 0
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    columnSpec = spec(GridLayout.UNDEFINED, 1f)
                    setMargins(12, 11, 12, 11)
                }
            }
            addView(dayTextView)
        }
    }

    private fun onDayClicked(day: Int, dayTextView: TextView) {
        if (selectedDays.contains(day)) {
            selectedDays.remove(day)
            dayTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
            dayTextView.background = ContextCompat.getDrawable(context, R.drawable.normal_day_shape)
        } else {
            selectedDays.add(day)
            dayTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
            dayTextView.background =
                ContextCompat.getDrawable(context, R.drawable.selected_day_shape)
        }
    }
}