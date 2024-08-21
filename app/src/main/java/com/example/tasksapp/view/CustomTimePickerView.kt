package com.example.tasksapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


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
        val outerRadius =
            (min(centerX.toDouble(), centerY.toDouble()) - 50).toInt() // Adjust outer radius
        val innerRadius =
            outerRadius - 100 // Adjust inner radius to create space for the inner circle

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

        // Draw 00 at the top
        paint!!.color = Color.BLACK
        paint!!.textSize = 40f
        canvas.drawText("00", centerX.toFloat(), (centerY - innerRadius + 45).toFloat(), paint!!)

        // Draw selected hour highlight
        val selectedAngle: Double
        val selectedX: Int
        val selectedY: Int

        if (selectedHour >= 13 && selectedHour <= 23) {
            selectedAngle =
                Math.toRadians(((selectedHour - 15) * 30).toDouble()) // Corrected the angle for inner circle
            selectedX = (centerX + innerRadius * 0.85 * cos(selectedAngle)).toInt()
            selectedY = (centerY + innerRadius * 0.85 * sin(selectedAngle)).toInt()
        } else if (selectedHour == 0) {
            selectedX = centerX
            selectedY = centerY - innerRadius + 45
        } else {
            selectedAngle = Math.toRadians(((selectedHour - 3) * 30).toDouble())
            selectedX = (centerX + outerRadius * 0.85 * cos(selectedAngle)).toInt()
            selectedY = (centerY + outerRadius * 0.85 * sin(selectedAngle)).toInt()
        }

        paint!!.color = Color.parseColor("#7B61FF")
        canvas.drawCircle(selectedX.toFloat(), selectedY.toFloat(), 40f, paint!!)

        paint!!.color = Color.WHITE
        canvas.drawText(
            String.format("%02d", selectedHour), selectedX.toFloat(), (selectedY + 15).toFloat(),
            paint!!
        )

        // Draw center time text
        paint!!.color = Color.BLACK
        paint!!.textSize = 60f
        canvas.drawText(
            String.format("%02d:%02d", selectedHour, selectedMinute),
            centerX.toFloat(),
            (centerY + 15).toFloat(),
            paint!!
        )
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
