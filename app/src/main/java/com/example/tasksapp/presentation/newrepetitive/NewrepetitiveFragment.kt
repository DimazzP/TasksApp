package com.example.tasksapp.presentation.newrepetitive

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentNewrepetitiveBinding
import com.example.tasksapp.databinding.FragmentNewtaskBinding
import com.example.tasksapp.presentation.main.MainViewModel
import com.example.tasksapp.presentation.newtask.NewtaskViewModel
import com.example.tasksapp.presentation.newtask.adapter.CalendarAdapter
import com.example.tasksapp.presentation.newtask.model.CalendarDay
import java.util.Calendar

class NewrepetitiveFragment : Fragment() {

    private val viewModel: NewrepetitiveViewModel by viewModels()
    private lateinit var binding: FragmentNewrepetitiveBinding
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var calendarDays: MutableList<CalendarDay>
    private var calendar: Calendar = Calendar.getInstance()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentNewrepetitiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setBottomVisible(false)
        setVisibilityView()
    }

    private fun setVisibilityView() {
        binding.apply {
            radioDaily.isChecked = true
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
    private fun updateVisibilityRadioEvaluation(checkedId: Int) {
        when (checkedId) {
            binding.radioYesNo.id -> {
                binding.newrepTableNumeric.visibility = View.GONE
            }

            binding.radioNumeric.id -> {
                binding.newrepTableNumeric.visibility = View.VISIBLE
            }
        }
    }
    private fun updateVisibilityRadioFrequention(checkedId: Int) {
        binding.newrepTableDays.visibility = View.GONE
        binding.tableActivity.visibility = View.GONE

        when (checkedId) {
            binding.radioDaily.id -> {

            }

            binding.radioSpecificDays.id -> {
                binding.newrepTableDays.visibility = View.VISIBLE
            }

            binding.radioActivity.id -> {
                binding.tableActivity.visibility = View.VISIBLE
            }
        }
    }

}