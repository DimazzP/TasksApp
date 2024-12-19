package com.example.tasksapp.presentation.tasks.goals

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
import com.example.tasksapp.databinding.FragmentGoalsBinding
import com.example.tasksapp.domain.enums.EnumTask
import com.example.tasksapp.domain.model.GoalModel
import com.example.tasksapp.domain.model.TaskModel
import com.example.tasksapp.domain.model.utils.ActivityRest
import com.example.tasksapp.presentation.main.MainViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Calendar

class GoalsFragment : Fragment() {

    private val viewModel: GoalsViewModel by viewModels()
    private lateinit var binding: FragmentGoalsBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    private var freqWork = 0;
    private var freqTask = 0;
    private val weeklyCheck = mutableListOf<Int>()
    private var startDate: LocalDate = LocalDate.now()
    private var endDate: LocalDate? = null
    private var prioritySelected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGoalsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener(){
        binding.goalBtDateStart.setOnClickListener {
            showStartDialog()
        }
        binding.goalTvDate.setOnClickListener {
            showEndDialog()
        }
        binding.goalMore.setOnClickListener {
            createGoal()
        }
        binding.goalBtnback.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.goalBtAddPriority.setOnClickListener {
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
                binding.goalPriorityText.text = prioritySelected.toString()
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

        binding.goalSwitchDateEnd.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.tableDateEnd.visibility = View.VISIBLE
            } else {
                binding.tableDateEnd.visibility = View.GONE
            }
        }

        binding.goalBtnback.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun createGoal(){
        binding.apply {
            if (goalTitleEdit.text == null || goalTitleEdit.text?.isEmpty()==true) {
                Toast.makeText(
                    requireContext(),
                    "Nama Tugas Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val lastIdNewTask = mainViewModel.listGoalTask.value?.last()
            val finalIdNewTask = lastIdNewTask?.id?.plus(1) ?: 1
            val lastTaskModel = mainViewModel.listTask.value?.last()
            val finalIdTaskModel = lastTaskModel?.idTask?.plus(1) ?: 1
            var newActivityRest: ActivityRest? = null

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
            val goalTask = GoalModel(
                id = finalIdNewTask,
                title = goalTitleEdit.text.toString(),
                description = goalDescriptionEdit.text.toString(),
                startDate = startLocal!!,
                endDate = endLocal,
                remember = 0,
                priority = prioritySelected,
                goalTarget = null,
                teams = null
            )

            mainViewModel.addGoal(goalTask)

            mainViewModel.addTaskModel(
                TaskModel(
                    idTask = finalIdTaskModel,
                    subTask = null,
                    enumTask = EnumTask.NEW,
                    idKeyTask = finalIdNewTask,
                    titleTask = goalTitleEdit.text.toString(),
                    time = startLocal?.getHour()
                        .toString() + ":" + startLocal?.getMinute(),
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
                Log.d("printdate", "${startDate.month}")
                binding.goalTextDateStart.text = "${startDate.dayOfMonth}/${startDate.monthValue}/${startDate.year}"
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
                binding.goalTvDate.text = "${endDate!!.dayOfMonth}/${endDate!!.monthValue}/${endDate!!.year}"
                val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
                binding.goalEtDays.setText(daysBetween.toString())
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }
}