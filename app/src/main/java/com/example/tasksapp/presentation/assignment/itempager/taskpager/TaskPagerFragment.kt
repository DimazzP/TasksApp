package com.example.tasksapp.presentation.assignment.itempager.taskpager

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.databinding.FragmentTaskPagerBinding
import com.example.tasksapp.presentation.assignment.adapter.AdapterAssignment
import com.example.tasksapp.presentation.main.MainViewModel
import java.time.LocalDateTime

class TaskPagerFragment : Fragment() {

    companion object {
        fun newInstance() = TaskPagerFragment()
    }

    private val viewModel: TaskPagerViewModel by viewModels()
    private lateinit var binding: FragmentTaskPagerBinding
    private val mainViewModel: MainViewModel by activityViewModels()

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
        val adapterAssignment = AdapterAssignment(mainViewModel.listNewTask.value?.toList() ?: emptyList())
        binding.taskpagerRecycler.adapter = adapterAssignment
        binding.taskpagerRecycler.layoutManager = LinearLayoutManager(requireContext())
        mainViewModel.listNewTask.observe(viewLifecycleOwner, Observer { data->
            adapterAssignment.updateData(data)
        })
    }
}