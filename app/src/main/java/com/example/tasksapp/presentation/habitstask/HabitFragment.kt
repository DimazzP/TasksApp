package com.example.tasksapp.presentation.habitstask

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tasksapp.databinding.DlPriorityBinding
import com.example.tasksapp.databinding.FragmentHabitBinding
import com.example.tasksapp.domain.enums.EnumTask
import com.example.tasksapp.domain.model.HabitsTaskModel
import com.example.tasksapp.domain.model.TaskModel
import com.example.tasksapp.domain.model.utils.ActivityRest
import com.example.tasksapp.presentation.main.MainViewModel
import com.example.tasksapp.presentation.repetitivetask.adapter.CalendarAdapter
import com.example.tasksapp.presentation.repetitivetask.model.CalendarDay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Calendar

class HabitFragment : Fragment() {

    private val viewModel: HabitViewModel by viewModels()
    private lateinit var binding: FragmentHabitBinding
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var calendarDays: MutableList<CalendarDay>
    private var calendar: Calendar = Calendar.getInstance()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var freqWork = 0;
    private var freqTask = 0;
    private val weeklyCheck = mutableListOf<Int>()
    private var startDate: LocalDate = LocalDate.now()
    private var endDate: LocalDate? = null
    private var prioritySelected = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setBottomVisible(false)
        setVisibilityView()
        setClickListener()
        checkedWeekly()
        val nowTime = "${LocalDate.now().dayOfMonth}/${LocalDate.now().monthValue}/${LocalDate.now().year}"
        binding.habitTextDateStart.text = nowTime
        binding.habitTvDate.text = nowTime
    }

    private fun setVisibilityView() {
        binding.apply {
            radioDaily.isChecked = true
            radioYesNo.isChecked = true
            updateVisibilityRadioFrequention(radioDaily.id)

            habitRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                updateVisibilityRadioFrequention(checkedId)
            }

            habitRadioGroupEvaluations.setOnCheckedChangeListener { group, checkedId ->
                updateVisibilityRadioEvaluation(checkedId)
            }

            habitSwitchDateEnd.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    tableDateEnd.visibility = View.VISIBLE
                } else {
                    tableDateEnd.visibility = View.GONE
                }
            }

        }
    }

    private fun setClickListener(){
        binding.habitMore.setOnClickListener {
            createHabitTask()
            findNavController().popBackStack()
        }
        binding.habitBtnback.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.habitBtAddPriority.setOnClickListener {
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
                binding.habitPriorityText.text = prioritySelected.toString()
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
        binding.habitBtDateStart.setOnClickListener {
            showStartDialog()
        }
        binding.habitTvDate.setOnClickListener {
            showEndDialog()
        }
    }

    private fun updateVisibilityRadioEvaluation(checkedId: Int) {
        when (checkedId) {
            binding.radioYesNo.id -> {
                freqWork = 0
                binding.habitTableNumeric.visibility = View.GONE
            }

            binding.radioNumeric.id -> {
                freqWork = 1
                binding.habitTableNumeric.visibility = View.VISIBLE
            }
        }
    }
    private fun updateVisibilityRadioFrequention(checkedId: Int) {
        binding.habitTableDays.visibility = View.GONE
        binding.tableActivity.visibility = View.GONE

        when (checkedId) {
            binding.radioDaily.id -> {
                freqTask = 0
            }

            binding.radioSpecificDays.id -> {
                freqTask = 1
                binding.habitTableDays.visibility = View.VISIBLE
            }

            binding.radioActivity.id -> {
                freqTask = 2
                binding.tableActivity.visibility = View.VISIBLE
            }
        }
    }

    private fun createHabitTask(){
        binding.apply {
            if (habitTitleEdit.text == null || habitTitleEdit.text?.isEmpty()==true) {
                Toast.makeText(
                    requireContext(),
                    "Nama Tugas Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val lastIdNewTask = mainViewModel.listHabitsTask.value?.last()
            val finalIdNewTask = lastIdNewTask?.id?.plus(1) ?: 1
            val lastTaskModel = mainViewModel.listTask.value?.last()
            val finalIdTaskModel = lastTaskModel?.idTask?.plus(1) ?: 1
            var newActivityRest: ActivityRest? = null
            if (habitActivity.text.isNotEmpty() && habitRest.text.isNotEmpty()) {
                newActivityRest = ActivityRest(
                    habitActivity.text.toString().toInt(),
                    habitRest.text.toString().toInt()
                )
            }
            var startLocal: LocalDateTime? = null
            var endLocal: LocalDateTime? = null
            if (startDate != null) {
                startLocal = startDate.atStartOfDay()
                    .withHour(
                        7
                    ).withMinute(0)
            }
            if (endDate != null) {
                endLocal = endDate?.atStartOfDay()?.withHour(0)?.withMinute(0)
            }
            var numNumeric: Int? = null
            if (habitNumberNumeric.text.isNotEmpty()) {
                numNumeric = habitNumberNumeric.text.toString().toInt()
            }
            val habitTask = HabitsTaskModel(
                id = finalIdNewTask,
                title = habitTitleEdit.text.toString(),
                description = habitDescriptionEdit.text.toString(),
                frequencyWork = freqWork,
                evaluationNumeric = numNumeric,
                frequencyTask = freqTask,
                frequencyEveryWeek = weeklyCheck,
                frequencyActivityRest = newActivityRest,
                startDate = startDate,
                endDate = endDate,
                remember = 0,
                priority = prioritySelected
            )
            mainViewModel.addTaskModel(
                TaskModel(
                    idTask = finalIdTaskModel,
                    subTask = null,
                    enumTask = EnumTask.NEW,
                    idKeyTask = finalIdNewTask,
                    titleTask = habitTitleEdit.text.toString(),
                    time = startLocal?.getHour()
                        .toString() + ":" + startLocal?.getMinute(),
                    startDate = startLocal,
                    repetitive = false,
                    teams = emptyList()
                )
            )
            mainViewModel.addHabitsTask(habitTask)
        }
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

    private fun showStartDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                startDate = LocalDate.of(year, month, selectedDay)
                Log.d("printdate", "${startDate.month}")
                binding.habitTextDateStart.text = "${startDate.dayOfMonth}/${startDate.monthValue}/${startDate.year}"
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
                binding.habitTvDate.text = "${endDate!!.dayOfMonth}/${endDate!!.monthValue}/${endDate!!.year}"
                val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
                binding.habitEtDays.setText(daysBetween.toString())
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

}