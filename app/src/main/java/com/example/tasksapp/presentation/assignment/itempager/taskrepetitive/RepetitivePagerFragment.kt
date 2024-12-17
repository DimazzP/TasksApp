package com.example.tasksapp.presentation.assignment.itempager.taskrepetitive

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
import com.example.tasksapp.databinding.FragmentRepetitivePagerBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.presentation.assignment.adapter.AdapterRepetitive
import com.example.tasksapp.presentation.main.MainViewModel
import java.time.LocalDateTime

class RepetitivePagerFragment : Fragment() {

    companion object {
        fun newInstance() = RepetitivePagerFragment()
    }

    private val viewModel: RepetitivePagerViewModel by viewModels()
    private lateinit var binding: FragmentRepetitivePagerBinding
    private val mainViewModel: MainViewModel by activityViewModels()


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
        val adapterRep =  AdapterRepetitive(mainViewModel.listRepetitiveTask.value ?: emptyList())
        binding.repetitivepagerRecycler.adapter = adapterRep
        binding.repetitivepagerRecycler.layoutManager = LinearLayoutManager(requireContext())
        mainViewModel.listRepetitiveTask.observe(viewLifecycleOwner, Observer { data->
            adapterRep.updateData(data)
        })
    }
}