package com.example.tasksapp.presentation.detailtask

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasksapp.R

class DetailTaskFragment : Fragment() {

    private val viewModel: DetailTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail_task, container, false)
    }

    companion object {
        fun newInstance() = DetailTaskFragment()
    }

}