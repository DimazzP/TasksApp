package com.example.tasksapp.presentation.repetitivetask

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.databinding.DlPriorityBinding
import com.example.tasksapp.databinding.FragmentRepetitiveBinding
import com.example.tasksapp.domain.enums.EnumTask
import com.example.tasksapp.domain.model.NewTaskModel
import com.example.tasksapp.domain.model.RepetitiveTask
import com.example.tasksapp.domain.model.TaskModel
import com.example.tasksapp.domain.model.utils.ActivityRest
import com.example.tasksapp.presentation.main.MainViewModel
import com.example.tasksapp.presentation.repetitivetask.adapter.CalendarAdapter
import com.example.tasksapp.presentation.repetitivetask.model.CalendarDay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class RepetitiveFragment : Fragment() {

    private val viewModel: RepetitiveViewModel by viewModels()
    private lateinit var binding: FragmentRepetitiveBinding

    private var calendar: Calendar = Calendar.getInstance()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var startDate: LocalDate = LocalDate.now()
    private var endDate: LocalDate? = null
    private val weeklyCheck = mutableListOf<Int>()
    private var prioritySelected = 0
    var freqSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepetitiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setBottomVisible(false)

        setupListeners()
        setVisibilityView()
        val nowTime =
            "${LocalDate.now().dayOfMonth}/${LocalDate.now().monthValue}/${LocalDate.now().year}"
        binding.newrepTextDateStart.text = nowTime
        binding.newrepTvDate.text = nowTime
//        initTimePicker()
    }


    private fun setVisibilityView() {
        binding.apply {
            radioDaily.isChecked = true
            updateVisibility(radioDaily.id)

            newrepBtnback.setOnClickListener {
                findNavController().popBackStack()
            }

            newrepRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                updateVisibility(checkedId)
            }

            newrepSwitchDateEnd.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    tableDateEnd.visibility = View.VISIBLE
                } else {
                    tableDateEnd.visibility = View.GONE
                }
            }

            newrepMoreBottom.setOnClickListener {
                if (newrepNextConst.visibility == View.GONE) {
                    newrepNextConst.visibility = View.VISIBLE
                    newrepMoreBottom.setImageResource(R.drawable.ic_ios_arrow_top)
                } else {
                    newrepNextConst.visibility = View.GONE
                    newrepMoreBottom.setImageResource(R.drawable.ic_ios_arrow_bottom)
                }
            }
        }
    }

    private fun updateVisibility(checkedId: Int) {
        binding.newrepTableDays.visibility = View.GONE
        binding.tableMonthly.visibility = View.GONE
        binding.tableActivity.visibility = View.GONE
        binding.tableYear.visibility = View.GONE

        when (checkedId) {
            binding.radioDaily.id -> {
                freqSelected = 0
            }

            binding.radioSpecificDays.id -> {
                binding.newrepTableDays.visibility = View.VISIBLE
                freqSelected = 1
            }

            binding.radioSpecificDatesMonth.id -> {
                binding.tableMonthly.visibility = View.VISIBLE
                freqSelected = 2
            }

            binding.radioYear.id -> {
                binding.tableYear.visibility = View.VISIBLE
                freqSelected = 3
            }

            binding.radioActivity.id -> {
                binding.tableActivity.visibility = View.VISIBLE
                freqSelected = 4
            }
        }
    }

    private fun setupListeners() {

        binding.newrepMore.setOnClickListener {
            createNewTask()
            findNavController().popBackStack()
        }

        binding.newrepTvDate.setOnClickListener {
            showEndDialog()
        }

        checkedWeekly()

        binding.newrepBtAddPriority.setOnClickListener {
            val bindingPrio = DlPriorityBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(bindingPrio.root)

            val dialog = builder.create()
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            bindingPrio.dlprioBtCancel.setOnClickListener {
                dialog.dismiss()
            }

            bindingPrio.dlprioBtOk.setOnClickListener {
                prioritySelected = bindingPrio.dlprioNumberPriority.text.toString().toInt()
                dialog.dismiss()
            }

            bindingPrio.dlprioBtMinus.setOnClickListener {
                val currentValue = bindingPrio.dlprioNumberPriority.text.toString().toInt()
                if (currentValue > 0) {
                    bindingPrio.dlprioNumberPriority.text = (currentValue - 1).toString()
                }
            }

            bindingPrio.dlprioBtPlus.setOnClickListener {
                val currentValue = bindingPrio.dlprioNumberPriority.text.toString().toInt()
                bindingPrio.dlprioNumberPriority.text = (currentValue + 1).toString()
            }
            dialog.show()
        }
        binding.newrepBtDateStart.setOnClickListener {
            showStartDialog()
        }
        binding.newrepBtDateEnd.setOnClickListener {
            showEndDialog()
        }
    }

    private fun createNewTask() {
        binding.apply {
            if (newrepTitleEdit.text == null || newrepTitleEdit.text?.isEmpty() == true) {
                Toast.makeText(
                    requireContext(),
                    "Nama Tugas Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            var startLocal: LocalDateTime? = null
            var endLocal: LocalDateTime? = null
            if (startDate != null) {
                startLocal = startDate.atStartOfDay()
                    .withHour(7).withMinute(0)
            }
            if (endDate != null) {
                endLocal = endDate?.atStartOfDay()?.withHour(0)?.withMinute(0)
            }

            var newActivityRest: ActivityRest? = null
            if (newrepActivity.text.isNotEmpty() && newrepRest.text.isNotEmpty()) {
                newActivityRest = ActivityRest(
                    newrepActivity.text.toString().toInt(),
                    newrepRest.text.toString().toInt()
                )
            }
            var freqYearly: Int? = null
            if (newrepFreqYearEdit.text.isNotEmpty()) {
                freqYearly = newrepFreqYearEdit.text.toString().toInt()
            }
            val lastIdNewTask = mainViewModel.listNewTask.value?.last()
            val finalIdNewTask = lastIdNewTask?.id?.plus(1) ?: 1
            val lastTaskModel = mainViewModel.listTask.value?.last()
            val finalIdTaskModel = lastTaskModel?.idTask?.plus(1) ?: 1

            val newTask = RepetitiveTask(
                id = finalIdNewTask,
                title = newrepTitleEdit.text.toString(),
                description = newrepDescription.text.toString(),
                subTask = null,
                freqTask = freqSelected,
                startDate = startLocal!!,
                endDate = endLocal,
                freqActivityRest = newActivityRest,
                freqMontly = newrepSelectedCalendarView.getSelectedDays().toList(),
                freqWeekly = weeklyCheck,
                freqYearly = freqYearly,
                postpone = newrepCheckPostpone.isChecked,
                priority = prioritySelected,
                reminder = 0
            )
            mainViewModel.addRepetitive(newTask)
            mainViewModel.addTaskModel(
                TaskModel(
                    idTask = finalIdTaskModel,
                    subTask = null,
                    enumTask = EnumTask.NEW,
                    idKeyTask = finalIdNewTask,
                    titleTask = newrepTitleEdit.text.toString(),
                    time = startLocal.getHour()
                        .toString() + ":" + startLocal.getMinute(),
                    startDate = startLocal,
                    repetitive = false,
                    teams = emptyList()
                )
            )
        }
    }

    private fun showStartDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                startDate = LocalDate.of(year, month, selectedDay)
                binding.newrepTextDateStart.text =
                    "${startDate.dayOfMonth}/${startDate.monthValue}/${startDate.year}"
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showEndDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                endDate = LocalDate.of(year, month, selectedDay)
                binding.newrepTvDate.text =
                    "${endDate!!.dayOfMonth}/${endDate!!.monthValue}/${endDate!!.year}"
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun checkedWeekly() {
        binding.apply {
            checkboxMonday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    weeklyCheck.add(0)
                } else {
                    weeklyCheck.remove(1)
                }
            }
            checkboxTuesday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    weeklyCheck.add(1)
                } else {
                    weeklyCheck.remove(1)
                }
            }
            checkboxWednesday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    weeklyCheck.add(2)
                } else {
                    weeklyCheck.remove(2)
                }
            }
            checkboxThursday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    weeklyCheck.add(3)
                } else {
                    weeklyCheck.remove(3)
                }
            }
            checkboxFriday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    weeklyCheck.add(4)
                } else {
                    weeklyCheck.remove(4)
                }
            }
            checkboxSaturday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    weeklyCheck.add(5)
                } else {
                    weeklyCheck.remove(5)
                }
            }
            checkboxSunday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    weeklyCheck.add(6)
                } else {
                    weeklyCheck.remove(6)
                }
            }
        }
    }
}
