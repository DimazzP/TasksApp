package com.example.tasksapp.presentation.calendar

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentCalendarBinding
//import com.example.tasksapp.presentation.calendar.adapter.CalendarAdapterAdapt
//import com.example.tasksapp.presentation.calendar.adapter.CalendarDayAdapt
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var binding: FragmentCalendarBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calenCalendar.apply {
            val currentMonth = getCurrentMonth()
            val currentYear = getCurrentYear()
            setNoteForDate(2, "3 Tugas", currentMonth, currentYear)
            setNoteForDate(3, "2 Tugas", currentMonth, currentYear)
            setNoteForDate(7, "1 Tugas", currentMonth, currentYear)        }
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }

}