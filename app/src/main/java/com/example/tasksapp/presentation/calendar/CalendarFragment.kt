package com.example.tasksapp.presentation.calendar

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentCalendarBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.domain.model.MemberModel
import com.example.tasksapp.domain.model.ProgressModel
import com.example.tasksapp.domain.model.TaskModel
import com.example.tasksapp.presentation.home.adapter.AdapterTaskHome
import com.example.tasksapp.presentation.main.MainViewModel
import com.example.tasksapp.view.customcalendar.CustomCalendarView
//import com.example.tasksapp.presentation.calendar.adapter.CalendarAdapterAdapt
//import com.example.tasksapp.presentation.calendar.adapter.CalendarDayAdapt
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var binding: FragmentCalendarBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var adapterSubAssignment: AdapterSubTask
    private lateinit var customCalendarView: CustomCalendarView

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
            setNoteForDate(7, "1 Tugas", currentMonth, currentYear)
            setNoteForDate(19, "3 Tugas", currentMonth, currentYear)
        }
        initData()

    }

    private fun initData() {

        val taskAdapter = AdapterTaskHome(emptyList())

        binding.calenRcDailyTask.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
        binding.calenRcSubTask.layoutManager = LinearLayoutManager(requireContext())
        adapterSubAssignment = AdapterSubTask(requireContext())
        binding.calenRcSubTask.adapter = adapterSubAssignment
        findTask()
    }

    private fun findTask() {
        customCalendarView = binding.calenCalendar
        customCalendarView.onDateSelected = { date ->
            Log.d("getTaskCallTopMore", date.toString())
            val getTaskTop = mainViewModel.findTasksByDate(date, mainViewModel.listTask.value ?: emptyList())
            if(getTaskTop!=null){
                adapterSubAssignment.updateData(getTaskTop)
            }
        }
        val selectDate = binding.calenCalendar.getSelectedDateLocalDate()
        val getTaskTop = mainViewModel.findTasksByDate(selectDate, mainViewModel.listTask.value ?: emptyList())
        if(getTaskTop!=null){
            adapterSubAssignment.updateData(getTaskTop)
        }

        mainViewModel.listTask.observe(viewLifecycleOwner, Observer { data->
            val getTask = mainViewModel.findTasksByDate(selectDate, data)
            Log.d("getTaskCall", getTask.toString())
            if(getTask!=null){
                adapterSubAssignment.updateData(getTask)
            }
        })
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }

}