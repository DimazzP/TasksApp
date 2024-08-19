package com.example.tasksapp.view


import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.tasksapp.R

class CustomSearchEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : EditText(context, attrs, defStyleAttr) {

    private var searchIcon: Drawable? = null
    private var clearIcon: Drawable? = null

    init {
        // Set up initial icons
        searchIcon = ContextCompat.getDrawable(context, R.drawable.ic_search)
        clearIcon = ContextCompat.getDrawable(context, R.drawable.ic_clear)

        // Set the search icon as the initial drawableEnd
        setCompoundDrawablesWithIntrinsicBounds(null, null, searchIcon, null)

        // Add text change listener
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateClearIconVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Handle the clear icon click
        setOnTouchListener { v, event ->
            if (event.rawX >= (right - compoundPaddingRight)) {
                if (text.isNotEmpty()) {
                    text.clear()
                }
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }

        // Optional: Handle search action on keyboard
        setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun updateClearIconVisibility(s: CharSequence?) {
        if (s.isNullOrEmpty()) {
            // Show search icon
            setCompoundDrawablesWithIntrinsicBounds(null, null, searchIcon, null)
        } else {
            // Show clear icon
            setCompoundDrawablesWithIntrinsicBounds(null, null, clearIcon, null)
        }
    }

    private fun performSearch() {
        // Implement your search action here
        // Example: Log.d("CustomSearchEditText", "Search for: ${text.toString()}")
    }
}
