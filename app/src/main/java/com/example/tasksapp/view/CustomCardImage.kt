package com.example.tasksapp.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.tasksapp.R

class CustomCardImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val imageView: ImageView

    init {
        // Mengatur properti CardView
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )

        // Inisialisasi ImageView
        imageView = ImageView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
            contentDescription = context.getString(R.string.img_user1)
            background = ContextCompat.getDrawable(context, R.drawable.bg_circle)
            setImageResource(R.drawable.example_user) // Set gambar default
        }

        // Menambahkan ImageView ke dalam CardView
        addView(imageView)
    }

    // Fungsi untuk mengatur gambar pada ImageView
    fun setImageResource(resourceId: Int) {
        imageView.setImageResource(resourceId)
    }

    // Fungsi untuk mengatur background pada ImageView
    fun setImageBackground(resourceId: Int) {
        imageView.background = ContextCompat.getDrawable(context, resourceId)
    }

}
