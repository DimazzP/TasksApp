package com.example.tasksapp.presentation.detailtask

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.R
import com.example.tasksapp.databinding.DlPriorityBinding
import com.example.tasksapp.databinding.FragmentDetailTaskBinding
import com.example.tasksapp.domain.model.GoalInterval
import com.example.tasksapp.domain.model.GoalTarget
import com.example.tasksapp.domain.model.MemberModel
import com.example.tasksapp.domain.model.TeamTaskModel
import com.example.tasksapp.presentation.detailtask.adapter.AdapterDetailTask
import com.example.tasksapp.presentation.detailtask.adapter.AdapterDetailTaskMember
import com.example.tasksapp.presentation.main.MainViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import java.time.format.DateTimeFormatter

class DetailTaskFragment : Fragment() {

    private val viewModel: DetailTaskViewModel by viewModels()
    private lateinit var binding: FragmentDetailTaskBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val selectedRadio: Int = 0
    private lateinit var taskAdapter: AdapterDetailTask

    private lateinit var selectedTeam: TeamTaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setBottomVisible(false)
        selectedTeam = mainViewModel.selectedTeam!!
        initializeData()
        clickListener()
        taskAdapter =
            AdapterDetailTask(selectedTeam.goalTarget ?: emptyList(), { task, isChecked ->

            })
        binding.detaskRcTask.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        val dummyMembers = listOf(
            MemberModel(
                name = "Renaldi",
                photo = "https://www.perfocal.com/blog/content/images/2021/01/Perfocal_17-11-2019_TYWFAQ_100_standard-3.jpg",
                role = "Developer"
            ),
            MemberModel(
                name = "Retno",
                photo = "https://images.ctfassets.net/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg?w=1200&h=992&fl=progressive&q=70&fm=jpg",
                role = "Designer"
            ),
            MemberModel(
                name = "Renaldi",
                photo = "https://mrwallpaper.com/images/hd/cool-profile-pictures-panda-man-gsl2ntkjj3hrk84s.jpg",
                role = "Manager"
            )
        )

        val memberAdapter = AdapterDetailTaskMember(dummyMembers) {
            findNavController().navigate(R.id.action_detailTaskFragment_to_memberFragment)
        }
        binding.detaskRcTeam.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = memberAdapter
        }

        binding.detaskAddTarget.setOnClickListener {
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

        val dialog = dialogBuilder.create()
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

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
            val currentGoal = mainViewModel.selectedTeam
            if (currentGoal != null) {
                val updatedGoalTarget = currentGoal.goalTarget?.toMutableList() ?: mutableListOf()
                updatedGoalTarget.add(goalTarget)

                // Update GoalModel
                val updatedGoal = currentGoal.copy(goalTarget = updatedGoalTarget)

                // Cari indeks dan perbarui di LiveData
                val index = mainViewModel.listTeamTask.value?.indexOfFirst { it.id == currentGoal.id }
                if (index != null && index != -1) {
                    val updatedList = mainViewModel.listTeamTask.value?.toMutableList() ?: mutableListOf()
                    updatedList[index] = updatedGoal
                    mainViewModel.listTeamTask.postValue(updatedList)

                    // Perbarui subTask ke adapter jika ada
                    if (updatedGoal.goalTarget != null) {
                        selectedTeam = updatedGoal
                        taskAdapter.updateTasks(updatedGoal.goalTarget)
                    }

                    Toast.makeText(requireContext(), "Target ditambahkan", Toast.LENGTH_SHORT).show()
                    Log.d("panggillog", "Target ditambahkan: ${goalTarget.titleTarget}")
                } else {
                    Log.e("panggillog", "Goal dengan ID ${currentGoal.id} tidak ditemukan.")
                }
            }

            // Tutup dialog
            dialog.dismiss()
        }

        // Buat dan tampilkan dialog
        dialog.show()
    }



    private fun initializeData() {
        binding.detaskTitle.text = selectedTeam.title
        binding.detaskCalendar.text =
            selectedTeam.endDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        binding.detaskDescription.text = selectedTeam.description
    }

    private fun clickListener() {
        binding.detaskBtnback.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        fun newInstance() = DetailTaskFragment()
    }
}