package com.example.tasksapp.presentation.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentAssignmentBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.presentation.assignment.adapter.AdapterAssignment
import com.example.tasksapp.presentation.assignment.adapter.AssignmentPagerAdapter
import com.example.tasksapp.presentation.main.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDateTime

class AssignmentFragment : Fragment() {

    private val viewModel: AssignmentViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_assignment, container, false);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager2>(R.id.assign_view_pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.assign_tab_layout)

        val adapter = AssignmentPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Tugas"
                1 -> "Tugas Berulang"
                2 -> "Kebiasaan"
                else -> "Tugas"
            }
        }.attach()
    }

    companion object {
        fun newInstance() = AssignmentFragment()
    }
}