package com.example.tasksapp.presentation.detailtask

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.databinding.FragmentDetailTaskBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.presentation.detailtask.adapter.AdapterDetailTask
import java.time.LocalDateTime

class DetailTaskFragment : Fragment() {

    private val viewModel: DetailTaskViewModel by viewModels()
    private lateinit var binding: FragmentDetailTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentDateTime: LocalDateTime = LocalDateTime.now()
        val dummyDetail = listOf(
            DetailAssignmentModel("Membuat moodboard", currentDateTime, false),
            DetailAssignmentModel("Membuat wireframe", currentDateTime, false),
            DetailAssignmentModel("Membuat komponen desain", currentDateTime, false),
        )
        val taskAdapter = AdapterDetailTask(dummyDetail)

        binding.detaskRcTask.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        clickListener()
    }

    private fun clickListener(){
        binding.detaskBtnback.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        fun newInstance() = DetailTaskFragment()
    }

}