package com.example.tasksapp.presentation.assignment.itempager.taskrepetitive

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentRepetitivePagerBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.presentation.assignment.adapter.AdapterRepetitive
import java.time.LocalDateTime

class RepetitivePagerFragment : Fragment() {

    companion object {
        fun newInstance() = RepetitivePagerFragment()
    }

    private val viewModel: RepetitivePagerViewModel by viewModels()
    private lateinit var binding: FragmentRepetitivePagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepetitivePagerBinding.inflate(layoutInflater)
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
        binding.repetitivepagerRecycler.adapter = AdapterRepetitive(dummyDetail)
        binding.repetitivepagerRecycler.layoutManager = LinearLayoutManager(requireContext())
    }
}