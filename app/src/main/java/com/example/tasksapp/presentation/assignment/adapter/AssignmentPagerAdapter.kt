package com.example.tasksapp.presentation.assignment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tasksapp.presentation.assignment.itempager.taskhabitual.HabitualPagerFragment
import com.example.tasksapp.presentation.assignment.itempager.taskpager.TaskPagerFragment
import com.example.tasksapp.presentation.assignment.itempager.taskrepetitive.RepetitivePagerFragment

class AssignmentPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TaskPagerFragment()
            1 -> RepetitivePagerFragment()
            2 -> HabitualPagerFragment()
            else -> TaskPagerFragment()
        }
    }
}
