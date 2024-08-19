package com.example.tasksapp.presentation.addmember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.databinding.FragmentAddMemberBinding
import com.example.tasksapp.domain.model.MemberModel
import com.example.tasksapp.presentation.addmember.adapter.AdapterAddMember

class AddMemberFragment : Fragment() {

    private val viewModel: AddMemberViewModel by viewModels()
    private lateinit var binding: FragmentAddMemberBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMemberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.admemSearch.setLayoutParams(ActionBar.LayoutParams(Gravity.RIGHT))
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

        val adapter = AdapterAddMember(dummyMembers)
        binding.admemRcMember.adapter = adapter
        binding.admemRcMember.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        fun newInstance() = AddMemberFragment()
    }

}