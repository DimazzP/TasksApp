package com.example.tasksapp.view.customcalendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import java.util.Calendar
import java.util.Locale

data class CalendarDayAdapt(val day: Int, var notes: String?, val month: Int, val year: Int)

class CalendarAdapterAdapt(private var days: List<CalendarDayAdapt>, private val calendar: Calendar) :
    RecyclerView.Adapter<CalendarAdapterAdapt.CalendarViewHolderAdapt>() {

    class CalendarViewHolderAdapt(view: View) : RecyclerView.ViewHolder(view) {
        val tvDay: TextView = view.findViewById(R.id.cd_day)
        val tvNotes: TextView = view.findViewById(R.id.cday_notes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolderAdapt {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_day, parent, false)
        return CalendarViewHolderAdapt(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolderAdapt, position: Int) {
        val day = days[position]

        if (day.day == 0) {
            holder.tvDay.text = ""
            holder.tvNotes.visibility = View.GONE
            holder.itemView.background = null
        } else {
            holder.tvDay.text = day.day.toString()

            if (!day.notes.isNullOrEmpty()) {
                holder.tvNotes.text = day.notes
                holder.tvNotes.visibility = View.VISIBLE
            } else {
                holder.tvNotes.visibility = View.GONE
            }

            // Cek apakah hari ini adalah hari saat ini di bulan yang sama
            val todayCalendar = Calendar.getInstance(Locale.getDefault())
            val isToday = day.day == todayCalendar.get(Calendar.DAY_OF_MONTH) &&
                    day.month == todayCalendar.get(Calendar.MONTH) &&
                    day.year == todayCalendar.get(Calendar.YEAR)

            // Pastikan tanggal yang di-highlight adalah tanggal di bulan dan tahun yang sama
            if (isToday) {
                holder.tvDay.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                holder.tvDay.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_today)
            } else {
                holder.itemView.background = null
            }
        }
    }

    override fun getItemCount() = days.size

    // Method to update the calendar data
    fun updateCalendarData(newDays: List<CalendarDayAdapt>) {
        this.days = newDays
        notifyDataSetChanged()
    }
}
