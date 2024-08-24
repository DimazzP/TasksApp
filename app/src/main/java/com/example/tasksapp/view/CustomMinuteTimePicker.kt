package com.example.tasksapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.tasksapp.R
import kotlin.math.*

class CustomMinutePickerView : View {
    private var paint: Paint? = null
    private var selectedMinute = 0
    private var onMinuteSelectedListener: OnMinuteSelectedListener? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height
        val centerX = width / 2
        val centerY = height / 2
        val outerRadius = (min(centerX.toDouble(), centerY.toDouble()) - 50).toInt()

        // Draw outer circle
        paint!!.color = Color.parseColor("#ECEBFE")
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), outerRadius.toFloat(), paint!!)

        // Draw minute numbers (0 to 60 in steps of 5)
        paint!!.color = Color.BLACK
        paint!!.textSize = 40f

        for (i in 0 until 12) {
            val minuteValue = i * 5
            val angle = Math.toRadians(((i - 3) * 30).toDouble())
            val x = (centerX + outerRadius * 0.85 * cos(angle)).toInt()
            val y = (centerY + outerRadius * 0.85 * sin(angle)).toInt() + 15
            canvas.drawText(String.format("%02d", minuteValue), x.toFloat(), y.toFloat(), paint!!)
        }

        // Draw selected minute highlight
        val selectedAngle = Math.toRadians(((selectedMinute / 5 - 3) * 30).toDouble())
        val selectedX = (centerX + outerRadius * 0.85 * cos(selectedAngle)).toInt()
        val selectedY = (centerY + outerRadius * 0.85 * sin(selectedAngle)).toInt() + 15

        paint!!.color = ContextCompat.getColor(context, R.color.purple_deep)
        canvas.drawCircle(selectedX.toFloat(), selectedY.toFloat(), 40f, paint!!)

        paint!!.color = Color.WHITE
        canvas.drawText(String.format("%02d", selectedMinute), selectedX.toFloat(), (selectedY + 15).toFloat(), paint!!)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            val centerX = width / 2
            val centerY = height / 2
            val outerRadius = (min(centerX.toDouble(), centerY.toDouble()) - 50).toInt()

            // Check if the touch is within the minute circle
            for (i in 0 until 12) {
                val minuteValue = i * 5
                val angle = Math.toRadians(((i - 3) * 30).toDouble())
                val minuteX = (centerX + outerRadius * 0.85 * cos(angle)).toInt()
                val minuteY = (centerY + outerRadius * 0.85 * sin(angle)).toInt() + 15
                if (distance(x, y, minuteX.toFloat(), minuteY.toFloat()) < 40) {
                    setSelectedMinute(minuteValue)
                    onMinuteSelectedListener?.onMinuteSelected(minuteValue)
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
    }

    fun setSelectedMinute(minute: Int) {
        selectedMinute = minute
        invalidate()
    }

    fun getSelectedMinute(): Int {
        return selectedMinute
    }

    // Interface for the callback
    interface OnMinuteSelectedListener {
        fun onMinuteSelected(minute: Int)
    }

    // Setter for the callback
    fun setOnMinuteSelectedListener(listener: OnMinuteSelectedListener) {
        this.onMinuteSelectedListener = listener
    }
}
