package com.example.tasksapp.presentation.calendar

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentCalendarBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.domain.model.MemberModel
import com.example.tasksapp.domain.model.ProgressModel
import com.example.tasksapp.presentation.home.adapter.AdapterTaskHome
//import com.example.tasksapp.presentation.calendar.adapter.CalendarAdapterAdapt
//import com.example.tasksapp.presentation.calendar.adapter.CalendarDayAdapt
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calenCalendar.apply {
            val currentMonth = getCurrentMonth()
            val currentYear = getCurrentYear()
            setNoteForDate(2, "3 Tugas", currentMonth, currentYear)
            setNoteForDate(3, "2 Tugas", currentMonth, currentYear)
            setNoteForDate(7, "1 Tugas", currentMonth, currentYear)
        }
        initData()

    }

    private fun initData(){
        val currentDateTime: LocalDateTime = LocalDateTime.now()

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

        val dummyDetail = listOf(
            DetailAssignmentModel("Membuat moodboard", currentDateTime, false),
            DetailAssignmentModel("Membuat wireframe", currentDateTime, false),
            DetailAssignmentModel("Membuat komponen desain", currentDateTime, false),
        )
        val dummyData = listOf(
            ProgressModel(
                title = "Desain UI",
                progress = 70,
                member = dummyMembers,
                detailAssignment = dummyDetail
            ),
            ProgressModel(
                title = "Tugas Laravel",
                progress = 40,
                member = dummyMembers,
                detailAssignment = dummyDetail
            ),
            ProgressModel(
                title = "Tugas Android",
                progress = 60,
                member = dummyMembers,
                detailAssignment = dummyDetail
            ),
            ProgressModel(
                title = "Desain UI",
                progress = 70,
                member = dummyMembers,
                detailAssignment = dummyDetail
            ),
            ProgressModel(
                title = "Tugas Laravel",
                progress = 40,
                member = dummyMembers,
                detailAssignment = dummyDetail
            ),
            ProgressModel(
                title = "Tugas Android",
                progress = 60,
                member = dummyMembers,
                detailAssignment = dummyDetail
            ),
        )
        val taskAdapter = AdapterTaskHome(dummyDetail)

        binding.calenRcDailyTask.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
        binding.calenRcSubTask.layoutManager = LinearLayoutManager(requireContext())
        val adapterSubAssignment = AdapterSubTask(requireContext(), dummyData)
        binding.calenRcSubTask.adapter = adapterSubAssignment
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }

}