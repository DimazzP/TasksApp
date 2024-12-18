package com.example.tasksapp.presentation.detailtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.AdpDetailTaskBinding
import com.example.tasksapp.domain.model.GoalTarget
class AdapterDetailTask(
    tasks: List<GoalTarget>, // Ubah parameter menjadi List
    private val onTaskCheckedChanged: (GoalTarget, Boolean) -> Unit
) : RecyclerView.Adapter<AdapterDetailTask.TaskViewHolder>() {

    private val tasks: MutableList<GoalTarget> = tasks.toMutableList() // Salin ke MutableList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = AdpDetailTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(private val binding: AdpDetailTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: GoalTarget) {
            binding.adpTaskDescription.text = task.titleTarget

            // Set listener untuk checkbox
            binding.adpTaskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                onTaskCheckedChanged(task, isChecked) // Panggil callback
            }
        }
    }

    fun updateTasks(newTasks: List<GoalTarget>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }
}
