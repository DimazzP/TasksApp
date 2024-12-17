package com.example.tasksapp.presentation.assignment.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.AdpRepetitiveAssignmentBinding
import com.example.tasksapp.domain.model.RepetitiveTask
import java.time.format.DateTimeFormatter
import java.util.Locale


class AdapterRepetitive(private var tasks: List<RepetitiveTask>) :
    RecyclerView.Adapter<AdapterRepetitive.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = AdpRepetitiveAssignmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    // Fungsi untuk memperbarui data dan memberi tahu adapter
    fun updateData(newTasks: List<RepetitiveTask>) {
        this.tasks = newTasks
        notifyDataSetChanged() // Memberitahukan adapter agar me-refresh data
    }

    inner class TaskViewHolder(private val binding: AdpRepetitiveAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: RepetitiveTask) {
            binding.adprepetTxtTitle.text = task.title
            if(task.endDate!=null){
                val formatter = "${task.startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale("id", "ID")))} - ${task.endDate?.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale("id", "ID")))}"
                binding.adprepetTaskTime.text = formatter
            }else{
                val formatter = task.startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale("id", "ID")))
                binding.adprepetTaskTime.text = formatter
            }
        }
    }
}
