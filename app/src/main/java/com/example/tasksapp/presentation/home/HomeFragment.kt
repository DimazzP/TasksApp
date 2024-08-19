package com.example.tasksapp.presentation.home

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tasksapp.R
import com.example.tasksapp.databinding.DlHomeAddtaskBinding
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
        adapterProgress = AdapterProgress(requireContext(), dummyData, binding.homViewPager) {
            findNavController().navigate(R.id.action_homeFragment_to_detailTaskFragment)
        }

        val viewPager2 = binding.homViewPager

        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
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
                if (position + 1 < dummyData.size) {
                    adapterProgress.notifyItemChanged(position + 1)
                }
                if (position - 1 >= 0) {
                    adapterProgress.notifyItemChanged(position - 1)
                }
            }
        })
        setClickListener()
    }

    private fun setClickListener(){
        binding.homAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        // Inflate layout menggunakan ViewBinding
        val bindingDialog = DlHomeAddtaskBinding.inflate(layoutInflater)

        // Buat AlertDialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(bindingDialog.root)
            .create()
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        // Setel elemen-elemen di dalam dialog menggunakan bindingDialog
        // Misalnya, mengatur listener untuk tombol di dalam dialog
        bindingDialog.hmaddtaskBtnTask.setOnClickListener {
            // Aksi ketika tombol task diklik
        }

        bindingDialog.hmaddtaskBtnRepetitive.setOnClickListener {
            // Aksi ketika tombol repetitive diklik
        }

        bindingDialog.hmaddtaskBtnHabit.setOnClickListener {
            // Aksi ketika tombol habit diklik
        }

        // Tampilkan dialog
        dialog.show()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}