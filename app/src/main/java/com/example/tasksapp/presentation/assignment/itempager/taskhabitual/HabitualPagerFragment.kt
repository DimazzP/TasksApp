package com.example.tasksapp.presentation.assignment.itempager.taskhabitual

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentHabitualPagerBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.presentation.assignment.adapter.AdapterHabitual
import com.example.tasksapp.presentation.assignment.adapter.AdapterRepetitive
import com.example.tasksapp.presentation.main.MainViewModel
import java.time.LocalDateTime

class HabitualPagerFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = HabitualPagerFragment()
    }

    private val viewModel: HabitualPagerViewModel by viewModels()
    private lateinit var binding: FragmentHabitualPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitualPagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData(){
        val adapterHabit = AdapterHabitual(mainViewModel.listHabitsTask.value ?: emptyList())
        binding.hapagerRecycler.adapter = adapterHabit
        binding.hapagerRecycler.layoutManager = LinearLayoutManager(requireContext())
        mainViewModel.listHabitsTask.observe(viewLifecycleOwner, Observer { data->
            adapterHabit.updateData(data)
        })
    }
}