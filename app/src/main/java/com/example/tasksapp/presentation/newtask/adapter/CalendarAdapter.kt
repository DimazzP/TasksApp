package com.example.tasksapp.presentation.newtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.databinding.AdpItemDayBinding
import com.example.tasksapp.presentation.newtask.model.CalendarDay

class CalendarAdapter(
    private val days: List<CalendarDay>,
    private val screenWidth: Int,
    private val onDayClick: (CalendarDay) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.DayViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = AdpItemDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day,position == selectedPosition)
    }

    override fun getItemCount(): Int = days.size
    fun setSelectedPosition(position: Int) {
        if (selectedPosition != position) {
            notifyItemChanged(selectedPosition)
            selectedPosition = position
            notifyItemChanged(selectedPosition)
        }
    }
    inner class DayViewHolder(private val binding: AdpItemDayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(day: CalendarDay, isSelected: Boolean) {
            binding.adpDateNumber.text = day.day.toString()
            binding.adpDayName.text = day.dayOfWeek
            val backgroundColor = if (isSelected) R.color.purple_deep else R.color.white
            binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, backgroundColor))
            itemView.setOnClickListener {
                onDayClick(day)
            }
        }
    }
}
