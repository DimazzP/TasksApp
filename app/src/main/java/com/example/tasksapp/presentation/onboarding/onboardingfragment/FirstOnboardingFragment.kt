package com.example.tasksapp.presentation.onboarding.onboardingfragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasksapp.R

class FirstOnboardingFragment : Fragment() {

    companion object {
        fun newInstance() = FirstOnboardingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_first_onboarding, container, false)
    }
}