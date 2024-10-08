package com.example.tasksapp.presentation.detailtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.AdpDetailTaskBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import java.time.format.DateTimeFormatter

class AdapterDetailTask(private val tasks: List<DetailAssignmentModel>) :
    RecyclerView.Adapter<AdapterDetailTask.TaskViewHolder>() {

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
        fun bind(task: DetailAssignmentModel) {
            binding.adpTaskDescription.text = task.name
        }
    }
}
