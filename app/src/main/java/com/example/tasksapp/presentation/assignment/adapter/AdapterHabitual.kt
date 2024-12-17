package com.example.tasksapp.presentation.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.AdpHabitualAssignmentBinding
import com.example.tasksapp.domain.model.HabitsTaskModel
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdapterHabitual(private var tasks: List<HabitsTaskModel>) :
    RecyclerView.Adapter<AdapterHabitual.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = AdpHabitualAssignmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    // Fungsi untuk mengupdate data
    fun updateData(newTasks: List<HabitsTaskModel>) {
        tasks = newTasks
        notifyDataSetChanged()  // Memberitahukan adapter untuk memperbarui tampilan
    }

    inner class TaskViewHolder(private val binding: AdpHabitualAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: HabitsTaskModel) {
            binding.adphabTxtTitle.text = task.title
            if(task.endDate!=null){
                val formatter = "${task.startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale("id", "ID")))} - ${task.endDate?.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale("id", "ID")))}"
                binding.adphabTaskTime.text = formatter
            }else{
                val formatter = task.startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale("id", "ID")))
                binding.adphabTaskTime.text = formatter
            }
        }
    }
}
