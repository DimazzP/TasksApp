package com.example.tasksapp.presentation.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.AdpAssignmentBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.domain.model.NewTaskModel
import java.time.format.DateTimeFormatter

class AdapterAssignment(private var tasks: List<NewTaskModel>) :
    RecyclerView.Adapter<AdapterAssignment.TaskViewHolder>() {

    // Method to update the data in the adapter
    fun updateData(newTasks: List<NewTaskModel>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = AdpAssignmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(private val binding: AdpAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: NewTaskModel) {
            binding.adpasTitle.text = task.title
            val formattedTime: String = task.startDate.format(DateTimeFormatter.ofPattern("HH:mm"))
            binding.adpasTaskTime.text = formattedTime
        }
    }
}
