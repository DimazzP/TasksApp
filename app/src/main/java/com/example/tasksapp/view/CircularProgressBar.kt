package com.example.tasksapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CircularProgressBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var progress = 0f // Progress dari 0 hingga 100
    private val strokeWidth = 20f // Ketebalan garis lingkaran

    // Paint untuk lingkaran background
    private val backgroundPaint = Paint().apply {
        color = Color.parseColor("#D0BFFF") // Warna ungu muda
        style = Paint.Style.STROKE
        strokeWidth = this@CircularProgressBar.strokeWidth
        isAntiAlias = true
    }

    // Paint untuk lingkaran progress
    private val progressPaint = Paint().apply {
        color = Color.parseColor("#FFFFFF") // Warna putih
        style = Paint.Style.STROKE
        strokeWidth = this@CircularProgressBar.strokeWidth
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND // Membuat ujung lingkaran bulat
    }

    // Paint untuk teks progress
    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 64f
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val size = min(width, height)
        val radius = (size / 2f) - strokeWidth

        // Menggambar lingkaran background
        canvas.drawCircle(width / 2f, height / 2f, radius, backgroundPaint)

        // Menggambar progress lingkaran
        val sweepAngle = (progress / 100f) * 360f
        canvas.drawArc(
            strokeWidth,
            strokeWidth,
            width - strokeWidth,
            height - strokeWidth,
            -90f,
            sweepAngle,
            false,
            progressPaint
        )

        // Menggambar teks progress
        canvas.drawText(
            "${progress.toInt()}%",
            width / 2f,
            height / 2f + (textPaint.textSize / 3),
            textPaint
        )
    }

    // Metode untuk memperbarui progress
    fun setProgress(newProgress: Float) {
        progress = newProgress.coerceIn(0f, 100f) // Batasi nilai progress antara 0 dan 100
        invalidate() // Refresh tampilan
    }
}
