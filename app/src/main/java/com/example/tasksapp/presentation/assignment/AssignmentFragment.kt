package com.example.tasksapp.presentation.assignment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tasksapp.R
import com.example.tasksapp.presentation.main.MainViewModel

class AssignmentFragment : Fragment() {

    companion object {
        fun newInstance() = AssignmentFragment()
    }

    private val viewModel: AssignmentViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_assignment, container, false)
    }
}