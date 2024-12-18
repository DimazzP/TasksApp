package com.example.tasksapp.presentation.tasks.goals.detailsgoal

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentDetailGoalBinding
import com.example.tasksapp.domain.model.GoalInterval
import com.example.tasksapp.domain.model.GoalModel
import com.example.tasksapp.domain.model.GoalTarget
import com.example.tasksapp.presentation.main.MainViewModel
import com.google.android.material.button.MaterialButtonToggleGroup

class DetailGoalFragment : Fragment() {

    companion object {
        fun newInstance() = DetailGoalFragment()
    }

    private val viewModel: DetailGoalViewModel by viewModels()
    private lateinit var binding: FragmentDetailGoalBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var goalModel: GoalModel
    private lateinit var adapter: GoalTargetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailGoalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data dari MainViewModel
        goalModel = mainViewModel.selectedGoal!!

        // Tampilkan data pada elemen UI
        binding.detgoTitleGoal.text = goalModel.title
        binding.detgoDescription.text = goalModel.description
        binding.detgoEnded.text = goalModel.endDate?.toString() ?: "No End Date"

        // Inisialisasi adapter dan RecyclerView
        adapter = GoalTargetAdapter(goalModel.goalTarget ?: emptyList())
        binding.rvGoalTargets.adapter = adapter
        binding.rvGoalTargets.layoutManager = LinearLayoutManager(requireContext())

        // Tambahkan listener untuk tombol addTask
        binding.addTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        // Buat AlertDialog Builder
        val dialogBuilder = AlertDialog.Builder(requireContext())

        // Inflate layout custom untuk dialog
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_target, null)

        // Set layout custom ke dialog
        dialogBuilder.setView(dialogView)

        // Referensi elemen dari dialog layout
        val etNamaTarget = dialogView.findViewById<EditText>(R.id.et_nama_target)
        val rbIntervalPengukuran = dialogView.findViewById<RadioButton>(R.id.rb_interval_pengukuran)
        val rbSedangBerlangsung = dialogView.findViewById<RadioButton>(R.id.rb_sedang_berlangsung)
        val rbMataUang = dialogView.findViewById<RadioButton>(R.id.rb_mata_uang)
        val etMulai = dialogView.findViewById<EditText>(R.id.et_mulai)
        val etTarget = dialogView.findViewById<EditText>(R.id.et_target)
        val etMulaiUang = dialogView.findViewById<EditText>(R.id.et_mulai_uang)
        val etTargetUang = dialogView.findViewById<EditText>(R.id.et_target_uang)
        val layoutInterval = dialogView.findViewById<LinearLayout>(R.id.intervalLayout)
        val layoutSedangBerlangsung =
            dialogView.findViewById<MaterialButtonToggleGroup>(R.id.layout_sedang_berlangsung)
        val layoutMataUang = dialogView.findViewById<LinearLayout>(R.id.layout_mata_uang)

        // Tampilkan layout yang sesuai berdasarkan pilihan
        rbIntervalPengukuran.setOnClickListener {
            layoutInterval.visibility = View.VISIBLE
            layoutSedangBerlangsung.visibility = View.GONE
            layoutMataUang.visibility = View.GONE
        }

        rbSedangBerlangsung.setOnClickListener {
            layoutInterval.visibility = View.GONE
            layoutSedangBerlangsung.visibility = View.VISIBLE
            layoutMataUang.visibility = View.GONE
        }

        rbMataUang.setOnClickListener {
            layoutInterval.visibility = View.GONE
            layoutSedangBerlangsung.visibility = View.GONE
            layoutMataUang.visibility = View.VISIBLE
        }

        // Tambahkan aksi tombol selesai di dialog
        val btnSelesai = dialogView.findViewById<TextView>(R.id.tv_selesai)
        btnSelesai.setOnClickListener {
            // Validasi input
            val titleTarget = etNamaTarget.text.toString()
            if (titleTarget.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Nama target tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var selectFreq = 0
            var interval: GoalInterval? = null
            var alreadyFinish: Boolean? = null
            var currency: GoalInterval? = null

            if (rbIntervalPengukuran.isChecked) {
                selectFreq = 1
                val start = etMulai.text.toString().toIntOrNull() ?: 0
                val end = etTarget.text.toString().toIntOrNull() ?: 0
                interval = GoalInterval(start, end)
            } else if (rbSedangBerlangsung.isChecked) {
                selectFreq = 2
                alreadyFinish = layoutSedangBerlangsung.checkedButtonId == R.id.btn_selesai
            } else if (rbMataUang.isChecked) {
                selectFreq = 3
                val start = etMulaiUang.text.toString().toIntOrNull() ?: 0
                val end = etTargetUang.text.toString().toIntOrNull() ?: 0
                currency = GoalInterval(start, end)
            }

            // Buat instance GoalTarget
            val goalTarget = GoalTarget(
                selectFreq = selectFreq,
                titleTarget = titleTarget,
                interval = interval,
                alreadyFinish = alreadyFinish,
                currency = currency
            )

            // Tambahkan ke GoalModel yang ada
            val currentGoal = mainViewModel.selectedGoal
            if (currentGoal != null) {
                val updatedGoalTarget = currentGoal.goalTarget?.toMutableList() ?: mutableListOf()
                updatedGoalTarget.add(goalTarget)

                val updatedGoal = currentGoal.copy(goalTarget = updatedGoalTarget)
                mainViewModel.selectedGoal = updatedGoal

                // Perbarui adapter dengan data baru
                adapter = GoalTargetAdapter(updatedGoalTarget)
                binding.rvGoalTargets.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            // Tutup dialog
            Toast.makeText(requireContext(), "Target ditambahkan", Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
        }

        // Buat dan tampilkan dialog
        val dialog = dialogBuilder.create()
        dialog.show()
    }
}
