package com.example.tasksapp.presentation.detailtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.AdpDetailTaskBinding
import com.example.tasksapp.domain.model.utils.SubTask
class AdapterDetailTask(
    tasks: List<SubTask>, // Ubah parameter menjadi List
    private val onTaskCheckedChanged: (SubTask, Boolean) -> Unit
) : RecyclerView.Adapter<AdapterDetailTask.TaskViewHolder>() {

    private val tasks: MutableList<SubTask> = tasks.toMutableList() // Salin ke MutableList

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
        fun bind(task: SubTask) {
            binding.adpTaskDescription.text = task.name
            binding.adpTaskCheckbox.isChecked = task.isChecked

            // Set listener untuk checkbox
            binding.adpTaskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                onTaskCheckedChanged(task, isChecked) // Panggil callback
                task.isChecked = isChecked // Update status di data lokal
            }
        }
    }

    fun updateTasks(newTasks: List<SubTask>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }
}
