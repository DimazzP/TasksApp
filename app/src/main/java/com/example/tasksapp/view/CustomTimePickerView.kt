package com.example.tasksapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

class CustomTimePickerView : View {
    private var paint: Paint? = null
    private var selectedHour = 2
    private var selectedMinute = 0

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
        val innerRadius = outerRadius - 100

        // Draw outer circle
        paint!!.color = Color.parseColor("#ECEBFE")
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), outerRadius.toFloat(), paint!!)

        // Draw outer hour numbers (1 to 12)
        paint!!.color = Color.BLACK
        paint!!.textSize = 40f

        for (i in 1..12) {
            val angle = Math.toRadians(((i - 3) * 30).toDouble())
            val x = (centerX + outerRadius * 0.85 * cos(angle)).toInt()
            val y = (centerY + outerRadius * 0.85 * sin(angle)).toInt() + 15
            canvas.drawText(i.toString(), x.toFloat(), y.toFloat(), paint!!)
        }

        // Draw inner hour numbers (13 to 23 and 00)
        for (i in 13..23) {
            val angle = Math.toRadians(((i - 15) * 30).toDouble())
            val x = (centerX + innerRadius * 0.85 * cos(angle)).toInt()
            val y = (centerY + innerRadius * 0.85 * sin(angle)).toInt() + 15
            canvas.drawText(String.format("%02d", i), x.toFloat(), y.toFloat(), paint!!)
        }

        // Draw 00 at the top with added dp offset
        val dpToPx = context.resources.displayMetrics.density
        val offsetY = (4 * dpToPx).toInt()
        paint!!.color = Color.BLACK
        paint!!.textSize = 40f
        canvas.drawText("00", centerX.toFloat(), (centerY - innerRadius + 45 + offsetY).toFloat(), paint!!)

        // Draw selected hour highlight
        val selectedAngle: Double
        val selectedX: Int
        val selectedY: Int

        if (selectedHour >= 13 && selectedHour <= 23) {
            selectedAngle = Math.toRadians(((selectedHour - 15) * 30).toDouble())
            selectedX = (centerX + innerRadius * 0.85 * cos(selectedAngle)).toInt()
            selectedY = (centerY + innerRadius * 0.85 * sin(selectedAngle)).toInt()
        } else if (selectedHour == 0) {
            selectedX = centerX
            selectedY = centerY - innerRadius + 45 + offsetY
        } else {
            selectedAngle = Math.toRadians(((selectedHour - 3) * 30).toDouble())
            selectedX = (centerX + outerRadius * 0.85 * cos(selectedAngle)).toInt()
            selectedY = (centerY + outerRadius * 0.85 * sin(selectedAngle)).toInt()
        }

        paint!!.color = Color.parseColor("#7B61FF")
        canvas.drawCircle(selectedX.toFloat(), selectedY.toFloat(), 40f, paint!!)

        paint!!.color = Color.WHITE
        canvas.drawText(String.format("%02d", selectedHour), selectedX.toFloat(), (selectedY + 15).toFloat(), paint!!)

        // Draw center time text with purple background on hour
        paint!!.textSize = 60f
        val hourText = String.format("%02d", selectedHour)
        val minuteText = String.format(":%02d", selectedMinute)

        // Measure the width of the hour text and minute text
        val hourWidth = paint!!.measureText(hourText)
        val minuteWidth = paint!!.measureText(minuteText)

        // Calculate the total width of the combined hour and minute text
        val totalWidth = hourWidth + minuteWidth

        // Draw the purple background behind the hour text
        paint!!.color = Color.parseColor("#7B61FF")
        canvas.drawRect(centerX - totalWidth / 2,
            (centerY - 40).toFloat(), centerX - totalWidth / 2 + hourWidth,
            (centerY + 40).toFloat(), paint!!)

        // Draw the hour text on top of the purple background
        paint!!.color = Color.WHITE
        canvas.drawText(hourText, centerX - totalWidth / 2 + hourWidth / 2,
            (centerY + 15).toFloat(), paint!!)

        // Draw the minute text
        paint!!.color = Color.BLACK
        canvas.drawText(minuteText, centerX - totalWidth / 2 + hourWidth + minuteWidth / 2,
            (centerY + 15).toFloat(), paint!!)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            val centerX = width / 2
            val centerY = height / 2
            val outerRadius = (min(centerX.toDouble(), centerY.toDouble()) - 50).toInt()
            val innerRadius = outerRadius - 100

            // Check if the touch is within the outer circle
            for (i in 1..12) {
                val angle = Math.toRadians(((i - 3) * 30).toDouble())
                val hourX = (centerX + outerRadius * 0.85 * cos(angle)).toInt()
                val hourY = (centerY + outerRadius * 0.85 * sin(angle)).toInt() + 15
                if (distance(x, y, hourX.toFloat(), hourY.toFloat()) < 40) {
                    setSelectedHour(i)
                    return true
                }
            }

            // Check if the touch is within the inner circle
            for (i in 13..23) {
                val angle = Math.toRadians(((i - 15) * 30).toDouble())
                val hourX = (centerX + innerRadius * 0.85 * cos(angle)).toInt()
                val hourY = (centerY + innerRadius * 0.85 * sin(angle)).toInt() + 15
                if (distance(x, y, hourX.toFloat(), hourY.toFloat()) < 40) {
                    setSelectedHour(i)
                    return true
                }
            }

            // Check if the touch is on 00
            val dpToPx = context.resources.displayMetrics.density
            val offsetY = (4 * dpToPx).toInt()
            val zeroX = centerX
            val zeroY = centerY - innerRadius + 45 + offsetY
            if (distance(x, y, zeroX.toFloat(), zeroY.toFloat()) < 40) {
                setSelectedHour(0)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
    }

    fun setSelectedHour(hour: Int) {
        selectedHour = hour
        invalidate()
    }

    fun setSelectedMinute(minute: Int) {
        selectedMinute = minute
        invalidate()
    }
}
