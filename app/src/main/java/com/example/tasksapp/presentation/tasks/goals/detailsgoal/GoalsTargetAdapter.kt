package com.example.tasksapp.presentation.tasks.goals.detailsgoal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.domain.model.GoalTarget

class GoalTargetAdapter(
    private val goalTargets: List<GoalTarget>
) : RecyclerView.Adapter<GoalTargetAdapter.GoalTargetViewHolder>() {

    inner class GoalTargetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val targetName: TextView = view.findViewById(R.id.tv_target_name)
        val progressText: TextView = view.findViewById(R.id.tv_progress)
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalTargetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_item_target, parent, false)
        return GoalTargetViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalTargetViewHolder, position: Int) {
        val goalTarget = goalTargets[position]

        holder.targetName.text = goalTarget.titleTarget

        // Tentukan progress berdasarkan tipe target
        when (goalTarget.selectFreq) {
            0 -> {
                val current = goalTarget.interval?.start ?: 0
                val total = goalTarget.interval?.end ?: 0
                holder.progressText.text = "IDR $current/$total"
                holder.progressBar.max = total
                holder.progressBar.progress = current
            }
            1 -> {
                val status = if (goalTarget.alreadyFinish == true) "Selesai" else "Berlangsung"
                holder.progressText.text = status
                holder.progressBar.visibility = View.GONE
            }
            2 -> {
                val current = goalTarget.currency?.start ?: 0
                val total = goalTarget.currency?.end ?: 0
                holder.progressText.text = "IDR $current/$total"
                holder.progressBar.max = total
                holder.progressBar.progress = current
            }
        }
    }

    override fun getItemCount(): Int = goalTargets.size
}
