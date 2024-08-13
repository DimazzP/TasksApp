package com.example.tasksapp.presentation.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentHomeBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.domain.model.MemberModel
import com.example.tasksapp.domain.model.ProgressModel
import com.example.tasksapp.presentation.home.adapter.AdapterProgress
import com.example.tasksapp.presentation.home.adapter.AdapterTaskHome
import java.time.LocalDateTime


class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterProgress: AdapterProgress

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentDateTime: LocalDateTime = LocalDateTime.now()
        val dummyMembers = listOf(
            MemberModel(
                name = "Renaldi",
                photo = "https://example.com/avatar1.jpg",
                role = "Developer"
            ),
            MemberModel(
                name = "Renaldi",
                photo = "https://example.com/avatar2.jpg",
                role = "Designer"
            ),
            MemberModel(
                name = "Renaldi",
                photo = "https://example.com/avatar3.jpg",
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
        adapterProgress = AdapterProgress(requireContext(), dummyData, binding.homViewPager){
            findNavController().navigate(R.id.action_homeFragment_to_detailTaskFragment)
        }
        binding.homViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val viewPager2 = binding.homViewPager

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val itemWidth = screenWidth * 0.65

        val largeMargin =
            (screenWidth - itemWidth).toInt()


        viewPager2.setPageTransformer { page, position ->
            page.setTranslationX((-position * page.width * 0.6).toFloat())
            page.setScaleY(1 - (0.15f * Math.abs(position)))
        }

        viewPager2.setOffscreenPageLimit(2)

        viewPager2.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) {
                    outRect.left = 0
                    outRect.right = largeMargin
                } else {
                    outRect.left = 0
                    outRect.right = largeMargin
                }
            }
        })
        binding.homViewPager.adapter = adapterProgress

        val taskAdapter = AdapterTaskHome(dummyDetail)

        binding.homRcTask.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                adapterProgress.notifyItemChanged(position)
                if(position + 1 < dummyData.size){
                    adapterProgress.notifyItemChanged(position+1)
                }
                if(position - 1 >= 0){
                    adapterProgress.notifyItemChanged(position-1)
                }
            }
        })
    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}