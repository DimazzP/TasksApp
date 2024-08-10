package com.example.tasksapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.AdpTaskBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import java.time.format.DateTimeFormatter

class TaskAdapter(private val tasks: List<DetailAssignmentModel>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = AdpTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(private val binding: AdpTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: DetailAssignmentModel) {
            binding.adpTaskDescription.text = task.name
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime: String = task.time.format(formatter)
            binding.adpTaskTime.text = formattedTime
        }
    }
}
