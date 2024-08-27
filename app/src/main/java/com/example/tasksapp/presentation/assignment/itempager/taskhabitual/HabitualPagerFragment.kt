package com.example.tasksapp.presentation.assignment.itempager.taskhabitual

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentHabitualPagerBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.presentation.assignment.adapter.AdapterHabitual
import com.example.tasksapp.presentation.assignment.adapter.AdapterRepetitive
import java.time.LocalDateTime

class HabitualPagerFragment : Fragment() {

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
        val currentDateTime: LocalDateTime = LocalDateTime.now()
        val dummyDetail = listOf(
            DetailAssignmentModel("Membuat moodboard", currentDateTime, false),
            DetailAssignmentModel("Membuat wireframe", currentDateTime, false),
            DetailAssignmentModel("Membuat komponen desain", currentDateTime, false),
        )
        binding.hapagerRecycler.adapter = AdapterHabitual(dummyDetail)
        binding.hapagerRecycler.layoutManager = LinearLayoutManager(requireContext())
    }
}