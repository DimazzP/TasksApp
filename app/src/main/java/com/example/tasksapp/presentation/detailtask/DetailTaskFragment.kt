package com.example.tasksapp.presentation.detailtask

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentDetailTaskBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.domain.model.MemberModel
import com.example.tasksapp.presentation.detailtask.adapter.AdapterDetailTask
import com.example.tasksapp.presentation.detailtask.adapter.AdapterDetailTaskMember
import java.time.LocalDateTime

class DetailTaskFragment : Fragment() {

    private val viewModel: DetailTaskViewModel by viewModels()
    private lateinit var binding: FragmentDetailTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()

        val currentDateTime: LocalDateTime = LocalDateTime.now()
        val dummyDetail = listOf(
            DetailAssignmentModel("Membuat moodboard", currentDateTime, false),
            DetailAssignmentModel("Membuat wireframe", currentDateTime, false),
            DetailAssignmentModel("Membuat komponen desain", currentDateTime, false),
        )
        val taskAdapter = AdapterDetailTask(dummyDetail)

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

        val memberAdapter = AdapterDetailTaskMember(dummyMembers){
            findNavController().navigate(R.id.action_detailTaskFragment_to_memberFragment)
        }
        binding.detaskRcTeam.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false )
            adapter = memberAdapter
        }
    }

    private fun clickListener(){
        binding.detaskBtnback.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        fun newInstance() = DetailTaskFragment()
    }
}