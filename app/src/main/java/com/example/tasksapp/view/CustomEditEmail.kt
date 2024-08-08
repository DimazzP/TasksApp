package com.example.tasksapp.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.tasksapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomEditEmail @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    init {
        // Set TextInputLayout properties
        hint = "Email"
        boxBackgroundMode = BOX_BACKGROUND_OUTLINE
        setBoxStrokeColor(ContextCompat.getColor(context, R.color.purple_deep))
        endIconMode = END_ICON_CLEAR_TEXT
        shapeAppearanceModel = shapeAppearanceModel.toBuilder()
            .setAllCornerSizes(14f) // Adjust radius as needed
            .build()

        // Create and add TextInputEditText
        val editText = TextInputEditText(context).apply {
            id = generateViewId()
            inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        }
        addView(editText)
    }
}