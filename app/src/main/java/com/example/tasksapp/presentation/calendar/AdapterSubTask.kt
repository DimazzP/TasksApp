package com.example.tasksapp.presentation.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.domain.model.ProgressModel

class AdapterSubTask(
    private val context: Context,
    private val dataList: List<ProgressModel>
) : RecyclerView.Adapter<AdapterSubTask.SubTaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adp_sub_assignment, parent, false)
        return SubTaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubTaskViewHolder, position: Int) {
        val item = dataList[position]

        holder.titleTextView.text = item.title

        // Mengisi RadioGroup dengan detail assignment
        val namesList: List<String> = item.detailAssignment.map { it.name }
        populateRadioGroup(holder.radioGroup, namesList)

        // Mengatur button untuk menampilkan/menyembunyikan RadioGroup
        holder.dropButton.setOnClickListener {
            if (holder.radioGroup.visibility == View.GONE) {
                holder.radioGroup.visibility = View.VISIBLE
                holder.dropButton.setImageResource(R.drawable.ic_ios_arrow_top) // Ganti icon
            } else {
                holder.radioGroup.visibility = View.GONE
                holder.dropButton.setImageResource(R.drawable.ic_ios_arrow_bottom) // Ganti icon
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    private fun populateRadioGroup(radioGroup: RadioGroup, detailAssignment: List<String>) {
        radioGroup.removeAllViews() // Hapus semua RadioButton yang ada sebelumnya

        for (detail in detailAssignment) {
            val radioButton = RadioButton(context)
            radioButton.text = detail
            radioButton.setTextColor(ContextCompat.getColor(context, R.color.black))
            radioButton.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            radioButton.textSize = 16f

            radioGroup.addView(radioButton)
        }
    }

    // Inner class ViewHolder
    inner class SubTaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.adp_subas_title)
        val dropButton: ImageButton = view.findViewById(R.id.adp_subas_drop_button)
        val radioGroup: RadioGroup = view.findViewById(R.id.adp_subas_radio_group)
    }
}
