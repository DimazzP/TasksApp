package com.example.tasksapp.presentation.assignment.itempager.taskpager

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentTaskPagerBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.presentation.assignment.adapter.AdapterAssignment
import com.example.tasksapp.presentation.assignment.adapter.AdapterRepetitive
import java.time.LocalDateTime

class TaskPagerFragment : Fragment() {

    companion object {
        fun newInstance() = TaskPagerFragment()
    }

    private val viewModel: TaskPagerViewModel by viewModels()
    private lateinit var binding: FragmentTaskPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentTaskPagerBinding.inflate(layoutInflater)
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
        binding.taskpagerRecycler.adapter = AdapterAssignment(dummyDetail)
        binding.taskpagerRecycler.layoutManager = LinearLayoutManager(requireContext())
    }
}