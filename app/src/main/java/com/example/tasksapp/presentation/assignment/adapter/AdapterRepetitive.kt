package com.example.tasksapp.presentation.assignment.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.AdpRepetitiveAssignmentBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import java.time.format.DateTimeFormatter

class AdapterRepetitive(private val tasks: List<DetailAssignmentModel>) :
    RecyclerView.Adapter<AdapterRepetitive.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = AdpRepetitiveAssignmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(private val binding: AdpRepetitiveAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: DetailAssignmentModel) {
            binding.adprepetTxtTitle.text = task.name
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime: String = task.time.format(formatter)
            binding.adprepetTaskTime.text = formattedTime
        }
    }
}
