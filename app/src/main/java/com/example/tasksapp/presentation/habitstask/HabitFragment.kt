package com.example.tasksapp.presentation.habitstask

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
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
        binding.newrepTextDateStart.text = nowTime
        binding.newrepTvDate.text = nowTime
    }

    private fun setVisibilityView() {
        binding.apply {
            radioDaily.isChecked = true
            radioYesNo.isChecked = true
            updateVisibilityRadioFrequention(radioDaily.id)

            newrepRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                updateVisibilityRadioFrequention(checkedId)
            }

            newrepRadioGroupEvaluations.setOnCheckedChangeListener { group, checkedId ->
                updateVisibilityRadioEvaluation(checkedId)
            }

            newrepSwitchDateEnd.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    tableDateEnd.visibility = View.VISIBLE
                } else {
                    tableDateEnd.visibility = View.GONE
                }
            }

        }
    }

    private fun setClickListener(){
        binding.newrepMore.setOnClickListener {
            createHabitTask()
            findNavController().popBackStack()
        }
        binding.newrepBtnback.setOnClickListener {
            findNavController().popBackStack()
        }
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
        binding.newrepTvDate.setOnClickListener {
            showEndDialog()
        }
    }

    private fun updateVisibilityRadioEvaluation(checkedId: Int) {
        when (checkedId) {
            binding.radioYesNo.id -> {
                freqWork = 0
                binding.newrepTableNumeric.visibility = View.GONE
            }

            binding.radioNumeric.id -> {
                freqWork = 1
                binding.newrepTableNumeric.visibility = View.VISIBLE
            }
        }
    }
    private fun updateVisibilityRadioFrequention(checkedId: Int) {
        binding.newrepTableDays.visibility = View.GONE
        binding.tableActivity.visibility = View.GONE

        when (checkedId) {
            binding.radioDaily.id -> {
                freqTask = 0
            }

            binding.radioSpecificDays.id -> {
                freqTask = 1
                binding.newrepTableDays.visibility = View.VISIBLE
            }

            binding.radioActivity.id -> {
                freqTask = 2
                binding.tableActivity.visibility = View.VISIBLE
            }
        }
    }

    private fun createHabitTask(){
        binding.apply {
            if (newrepTitleEdit.text == null) {
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
            if (newrepActivity.text.isNotEmpty() && newrepRest.text.isNotEmpty()) {
                newActivityRest = ActivityRest(
                    newrepActivity.text.toString().toInt(),
                    newrepRest.text.toString().toInt()
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
            if (newrepNumberNumeric.text.isNotEmpty()) {
                numNumeric = newrepNumberNumeric.text.toString().toInt()
            }
            val habitTask = HabitsTaskModel(
                id = finalIdNewTask,
                title = newrepTitleEdit.text.toString(),
                description = newrepDescriptionEdit.text.toString(),
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
                    enumTask = EnumTask.NEW,
                    idKeyTask = finalIdNewTask,
                    titleTask = newrepTitleEdit.text.toString(),
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
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                startDate = LocalDate.of(year, month, selectedDay)
                binding.newrepTextDateStart.text = "${startDate.dayOfMonth}/${startDate.monthValue}/${startDate.year}"
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showEndDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                endDate = LocalDate.of(year, month, selectedDay)
                binding.newrepTvDate.text = "${endDate!!.dayOfMonth}/${endDate!!.monthValue}/${endDate!!.year}"
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

}