package com.example.tasksapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.tasksapp.R
import com.example.tasksapp.databinding.CustomTimePickerBinding

class CustomTimePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CustomTimePickerBinding =
        CustomTimePickerBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        initTimePicker()
    }

    private fun initTimePicker() {
        binding.timeHourPicker.setOnHourSelectedListener(object : CustomHourTimePicker.OnHourSelectedListener {
            override fun onHourSelected(hour: Int) {
                binding.timeHour.text = String.format("%02d", hour)
            }
        })

        binding.timeMinutePicker.setOnMinuteSelectedListener(object : CustomMinutePickerView.OnMinuteSelectedListener {
            override fun onMinuteSelected(minute: Int) {
                binding.timeMinute.text = String.format("%02d", minute)
            }
        })

        binding.timeHour.setOnClickListener {
            binding.timeHourPicker.visibility = View.VISIBLE
            binding.timeMinutePicker.visibility = View.INVISIBLE
            binding.timeHour.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_deep))
            binding.timeHour.setTextColor(ContextCompat.getColor(context, R.color.white))
            binding.timeMinute.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
            binding.timeMinute.setTextColor(ContextCompat.getColor(context, R.color.black))
        }

        binding.timeMinute.setOnClickListener {
            binding.timeHourPicker.visibility = View.INVISIBLE
            binding.timeMinutePicker.visibility = View.VISIBLE
            binding.timeMinute.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_deep))
            binding.timeMinute.setTextColor(ContextCompat.getColor(context, R.color.white))
            binding.timeHour.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
            binding.timeHour.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

    fun getHour(): Int {
        return binding.timeHour.text.toString().toInt()
    }

    fun getMinute(): Int {
        return binding.timeMinute.text.toString().toInt()
    }
}
