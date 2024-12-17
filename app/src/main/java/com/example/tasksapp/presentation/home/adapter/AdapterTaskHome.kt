package com.example.tasksapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.AdpHomeTaskBinding
import com.example.tasksapp.domain.model.TaskModel
import java.time.format.DateTimeFormatter

class AdapterTaskHome(private var tasks: List<TaskModel>) :
    RecyclerView.Adapter<AdapterTaskHome.TaskViewHolder>() {

    // Menambahkan metode untuk memperbarui data
    fun updateTasks(newTasks: List<TaskModel>) {
        tasks = newTasks
        notifyDataSetChanged()  // Memberitahukan RecyclerView untuk memperbarui daftar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = AdpHomeTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], position)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(private val binding: AdpHomeTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskModel, position: Int) {
            binding.adpTaskTitle.text = "Tugas ${position+1}"
            binding.adpTaskDescription.text = task.titleTask
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime: String? = task.startDate?.format(formatter)
            binding.adpTaskTime.text = formattedTime
        }
    }
}
