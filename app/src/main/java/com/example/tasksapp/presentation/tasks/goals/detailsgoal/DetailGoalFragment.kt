package com.example.tasksapp.presentation.tasks.goals.detailsgoal

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tasksapp.databinding.FragmentDetailGoalBinding
import com.example.tasksapp.domain.model.GoalModel
import com.example.tasksapp.presentation.main.MainViewModel

class DetailGoalFragment : Fragment() {

    companion object {
        fun newInstance() = DetailGoalFragment()
    }

    private val viewModel: DetailGoalViewModel by viewModels()
    private lateinit var binding: FragmentDetailGoalBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var goalModel: GoalModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailGoalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goalModel = mainViewModel.selectedGoal!!
    }

    private fun setClickListener(){

    }
}