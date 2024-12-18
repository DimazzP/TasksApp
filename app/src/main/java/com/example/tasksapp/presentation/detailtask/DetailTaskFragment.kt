package com.example.tasksapp.presentation.detailtask

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.R
import com.example.tasksapp.databinding.DialogAddTargetBinding
import com.example.tasksapp.databinding.DlPriorityBinding
import com.example.tasksapp.databinding.FragmentDetailTaskBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.domain.model.MemberModel
import com.example.tasksapp.domain.model.TeamTaskModel
import com.example.tasksapp.presentation.detailtask.adapter.AdapterDetailTask
import com.example.tasksapp.presentation.detailtask.adapter.AdapterDetailTaskMember
import com.example.tasksapp.presentation.main.MainViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailTaskFragment : Fragment() {

    private val viewModel: DetailTaskViewModel by viewModels()
    private lateinit var binding: FragmentDetailTaskBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val selectedRadio: Int = 0

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
        val taskAdapter =
            AdapterDetailTask(selectedTeam.subTask ?: emptyList(), { task, isChecked ->

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
            dialogTarget()
        }
    }

    private fun dialogTarget(){
        val bindingPrio = DialogAddTargetBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingPrio.root)
        val dialog = builder.create()
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        bindingPrio.rgTipeTarget.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                bindingPrio.rbIntervalPengukuran.id->{

                }
                bindingPrio.rbSedangBerlangsung.id->{

                }
                bindingPrio.rbMataUang.id->{

                }
            }
        }


        bindingPrio.tvSelesai.setOnClickListener {
            dialog.dismiss()
        }

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