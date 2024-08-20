package com.example.tasksapp.presentation.newtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.databinding.AdpItemDayBinding
import com.example.tasksapp.presentation.newtask.model.CalendarDay

class CalendarAdapter(
    private var days: MutableList<CalendarDay>,  // MutableList agar bisa diubah
    private val screenWidth: Int,
    private val recyclerView: RecyclerView,
    private val onDayClick: (CalendarDay) -> Unit,
) : RecyclerView.Adapter<CalendarAdapter.DayViewHolder>() {

    var selectedPositionCalendar = RecyclerView.NO_POSITION
    private var middleItemPosition = RecyclerView.NO_POSITION  // Properti untuk posisi item tengah

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
        holder.bind(day)
        val layoutParams = holder.itemView.layoutParams
        layoutParams.width = screenWidth / 5  // Set width to screen width / 5
        holder.itemView.layoutParams = layoutParams

        // Highlight jika posisi ini adalah middle item
        if (position == middleItemPosition) {
            holder.setSelected(true)
        } else {
            holder.setSelected(false)
        }
    }

    override fun getItemCount(): Int = days.size

    fun setMiddleItemPosition(position: Int) {
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager

        // Hapus highlight dari item tengah sebelumnya
        if (middleItemPosition != RecyclerView.NO_POSITION) {
            val previousMiddleItemView = layoutManager?.findViewByPosition(middleItemPosition)
            if (previousMiddleItemView != null) {
                val binding = AdpItemDayBinding.bind(previousMiddleItemView)
                binding.adpDateCard.setCardBackgroundColor(
                    ContextCompat.getColor(previousMiddleItemView.context, R.color.white)
                )
                val textColor = R.color.black
                binding.adpDateNumber.setTextColor(
                    ContextCompat.getColor(binding.root.context, textColor)
                )
                binding.adpDayName.setTextColor(
                    ContextCompat.getColor(binding.root.context, textColor)
                )
            } else {
                notifyItemChanged(middleItemPosition)
            }
        }

        // Set highlight untuk item tengah yang baru
        if (position != RecyclerView.NO_POSITION) {
            val newMiddleItemView = layoutManager?.findViewByPosition(position)
            newMiddleItemView?.let { view ->
                val binding = AdpItemDayBinding.bind(view)
                val textColor =  R.color.white
                binding.adpDateNumber.setTextColor(
                    ContextCompat.getColor(binding.root.context, textColor)
                )
                binding.adpDayName.setTextColor(
                    ContextCompat.getColor(binding.root.context, textColor)
                )
                binding.adpDateCard.setCardBackgroundColor(
                    ContextCompat.getColor(view.context, R.color.purple_deep)
                )
            }
        }

        middleItemPosition = position
    }

    fun updateDays(newDays: List<CalendarDay>) {
        days.clear()
        days.addAll(newDays)
        notifyDataSetChanged()
    }

    inner class DayViewHolder(private val binding: AdpItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(day: CalendarDay) {
            binding.adpDateNumber.text = day.day.toString()
            binding.adpDayName.text = day.dayOfWeek

            itemView.setOnClickListener {
                onDayClick(day)
            }
        }

        fun setSelected(isSelected: Boolean) {
            val backgroundColor = if (isSelected) R.color.purple_deep else R.color.white
            binding.adpDateCard.setCardBackgroundColor(
                ContextCompat.getColor(binding.root.context, backgroundColor)
            )
            val textColor = if (isSelected) R.color.white else R.color.black
            binding.adpDateNumber.setTextColor(
                ContextCompat.getColor(binding.root.context, textColor)
            )
            binding.adpDayName.setTextColor(
                ContextCompat.getColor(binding.root.context, textColor)
            )
        }
    }
}