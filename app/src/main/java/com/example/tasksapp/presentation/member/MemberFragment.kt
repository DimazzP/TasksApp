package com.example.tasksapp.presentation.member

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentMemberBinding

class MemberFragment : Fragment() {

    private val viewModel: MemberViewModel by viewModels()
    private lateinit var binding: FragmentMemberBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = MemberFragment()
    }
}