package com.example.tasksapp.presentation.tasks.goals.detailsgoal

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasksapp.databinding.FragmentDetailGoalBinding

class DetailGoalFragment : Fragment() {

    companion object {
        fun newInstance() = DetailGoalFragment()
    }

    private val viewModel: DetailGoalViewModel by viewModels()
    private lateinit var binding: FragmentDetailGoalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailGoalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setClickListener(){

    }
}