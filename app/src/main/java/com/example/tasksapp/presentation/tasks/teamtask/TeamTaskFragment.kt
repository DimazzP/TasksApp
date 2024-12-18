package com.example.tasksapp.presentation.tasks.teamtask

import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentTeamTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class TeamTaskFragment : Fragment() {

    private var _binding: FragmentTeamTaskBinding? = null
    private val binding get() = _binding!!

    private var selectedDates = mutableSetOf<Int>() // To keep track of selected dates
    private var priorityValue = 1 // Default priority value
    private var selectedReminderTime: String = "0" // Default reminder time

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rgFrequency.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_weekly -> {
                    binding.gridDaysOfWeek.visibility = View.VISIBLE
                    binding.gridDates.visibility = View.GONE
                    binding.tlYearlyDate.visibility = View.GONE
                    binding.linearCycle.visibility = View.GONE
                }
                R.id.rb_monthly -> {
                    binding.gridDates.visibility = View.VISIBLE
                    binding.gridDaysOfWeek.visibility = View.GONE
                    binding.tlYearlyDate.visibility = View.GONE
                    binding.linearCycle.visibility = View.GONE
                    populateDatesGrid()
                }
                R.id.rb_yearly -> {
                    binding.tlYearlyDate.visibility = View.VISIBLE
                    binding.gridDates.visibility = View.GONE
                    binding.gridDaysOfWeek.visibility = View.GONE
                    binding.linearCycle.visibility = View.GONE
                }
                R.id.rb_cycle -> {
                    binding.linearCycle.visibility = View.VISIBLE
                    binding.gridDates.visibility = View.GONE
                    binding.gridDaysOfWeek.visibility = View.GONE
                    binding.tlYearlyDate.visibility = View.GONE
                }
                else -> {
                    binding.gridDaysOfWeek.visibility = View.GONE
                    binding.gridDates.visibility = View.GONE
                    binding.tlYearlyDate.visibility = View.GONE
                    binding.linearCycle.visibility = View.GONE
                }
            }
        }

        binding.switchTanggalSelesai.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.linearTanggalSelesaiInput.visibility = View.VISIBLE
                displayCurrentDate()
            } else {
                binding.linearTanggalSelesaiInput.visibility = View.GONE
            }
        }

        binding.etDaysToAdd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val daysToAdd = s.toString().toIntOrNull() ?: 0
                calculateEndDate(daysToAdd)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.linearPrioritas.setOnClickListener { showPriorityDialog() }
        binding.linearPengingat.setOnClickListener { showReminderDialog() }

        binding.btnDone.setOnClickListener {
            val projectName = binding.taskNameEditText.text.toString()
            val projectDescription = binding.descriptionEditText.text.toString()
            val specificDate = if (binding.rbYearly.isChecked) binding.tvCurrentDate.text.toString() else null
            val activityDays = binding.etActivityDays.text.toString().toIntOrNull()
            val restDays = binding.etRestDays.text.toString().toIntOrNull()
            val endDate = binding.tvCurrentDate.text.toString()
            val reminderCount = selectedReminderTime.toIntOrNull()
            val priority = priorityValue.toString()

            if (projectName.isEmpty() || projectDescription.isEmpty()) {
                Toast.makeText(requireContext(), "Nama proyek dan deskripsi harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Tugas berhasil dibuat", Toast.LENGTH_SHORT).show()
                // Logic for saving or passing data to another fragment
            }
        }

        binding.btnAddMember.setOnClickListener {
            Toast.makeText(requireContext(), "Tambah anggota belum diimplementasikan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayCurrentDate() {
        val today = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.tvCurrentDate.text = dateFormat.format(today)
    }

    private fun calculateEndDate(daysToAdd: Int) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.tvCurrentDate.text = dateFormat.format(calendar.time)
    }

    private fun populateDatesGrid() {
        binding.gridDates.removeAllViews()
        for (i in 1..31) {
            val button = Button(requireContext()).apply {
                text = i.toString()
                textSize = 16f
                setBackgroundColor(Color.TRANSPARENT)
                setTextColor(Color.BLACK)
                setOnClickListener {
                    if (selectedDates.contains(i)) {
                        setBackgroundColor(Color.TRANSPARENT)
                        selectedDates.remove(i)
                    } else {
                        setBackgroundColor(Color.RED)
                        selectedDates.add(i)
                    }
                }
            }
            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            binding.gridDates.addView(button, params)
        }
    }

    private fun showPriorityDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_set_priority, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        val btnMinus = dialogView.findViewById<ImageButton>(R.id.btnMinus)
        val btnPlus = dialogView.findViewById<ImageButton>(R.id.btnPlus)
        val tvPriorityValue = dialogView.findViewById<TextView>(R.id.tvPriorityValue)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)

        tvPriorityValue.text = priorityValue.toString()

        btnMinus.setOnClickListener {
            if (priorityValue > 1) {
                priorityValue--
                tvPriorityValue.text = priorityValue.toString()
            }
        }

        btnPlus.setOnClickListener {
            if (priorityValue < 10) {
                priorityValue++
                tvPriorityValue.text = priorityValue.toString()
            }
        }

        btnCancel.setOnClickListener { alertDialog.dismiss() }

        btnOk.setOnClickListener {
            binding.btnPrioritas.text = priorityValue.toString()
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun showReminderDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_reminder, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        val tvTime = dialogView.findViewById<TextView>(R.id.tvTime)
        val rgType = dialogView.findViewById<RadioGroup>(R.id.rgType)
        val rgSchedule = dialogView.findViewById<RadioGroup>(R.id.rgSchedule)
        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        tvTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timePicker = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                tvTime.text = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            }, hour, minute, true)
            timePicker.show()
        }

        btnCancel.setOnClickListener { alertDialog.dismiss() }

        btnOk.setOnClickListener {
            selectedReminderTime = tvTime.text.toString()
            binding.btnPengingat.text = selectedReminderTime
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
