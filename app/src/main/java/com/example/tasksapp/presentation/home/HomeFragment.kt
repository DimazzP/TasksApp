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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tasksapp.R
import com.example.tasksapp.databinding.DlHomeAddtaskBinding
import com.example.tasksapp.databinding.DlLiterationBinding
import com.example.tasksapp.databinding.FragmentHomeBinding
import com.example.tasksapp.domain.model.DetailAssignmentModel
import com.example.tasksapp.domain.model.GoalModel
import com.example.tasksapp.domain.model.MemberModel
import com.example.tasksapp.domain.model.ProgressModel
import com.example.tasksapp.presentation.home.adapter.AdapterProgress
import com.example.tasksapp.presentation.home.adapter.AdapterTaskHome
import com.example.tasksapp.presentation.main.MainViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.abs


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterProgress: AdapterProgress
    var goalModel: List<GoalModel> = emptyList()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setBottomVisible(true)

//        viewPagerInitTeam()
        viewPagerInitGoal()
        setClickListener()
        setTimeListener()
    }

    private fun viewPagerInitGoal(){
        adapterProgress = AdapterProgress(requireContext(), emptyList(), binding.homViewPagerGoal) {
            mainViewModel.selectedGoal = it
            findNavController().navigate(R.id.action_homeFragment_to_detailGoalFragment)
        }
        mainViewModel.listGoalTask.observe(viewLifecycleOwner, Observer { data->
            adapterProgress.updateData(data)
        })
        binding.homViewPagerGoal.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val viewPager2 = binding.homViewPagerGoal

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val itemWidth = screenWidth * 0.65

        val largeMargin =
            (screenWidth - itemWidth).toInt()


        viewPager2.setPageTransformer { page, position ->
            page.translationX = (-position * page.width * 0.6).toFloat()
            page.scaleY = 1 - (0.15f * abs(position))
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
        binding.homViewPagerGoal.adapter = adapterProgress

        val taskAdapter = AdapterTaskHome(emptyList())
        mainViewModel.listTask.observe(viewLifecycleOwner, { newTaskList ->
            taskAdapter.updateTasks(newTaskList)
        })

        binding.homRcTask.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                adapterProgress.notifyItemChanged(position)
                if (position + 1 < goalModel.size) {
                    adapterProgress.notifyItemChanged(position + 1)
                }
                if (position - 1 >= 0) {
                    adapterProgress.notifyItemChanged(position - 1)
                }
            }
        })
    }

//    private fun viewPagerInitTeam(){
//        adapterProgress = AdapterProgress(requireContext(), emptyList(), binding.homViewPagerTeam) {
//            findNavController().navigate(R.id.action_homeFragment_to_detailTaskFragment)
//        }
//        mainViewModel.listGoalTask.observe(viewLifecycleOwner, Observer { data->
//            adapterProgress.updateData(data)
//        })
//        binding.homViewPagerTeam.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//
//        val viewPager2 = binding.homViewPagerTeam
//
//        val displayMetrics = resources.displayMetrics
//        val screenWidth = displayMetrics.widthPixels
//        val itemWidth = screenWidth * 0.65
//
//        val largeMargin =
//            (screenWidth - itemWidth).toInt()
//
//
//        viewPager2.setPageTransformer { page, position ->
//            page.translationX = (-position * page.width * 0.6).toFloat()
//            page.scaleY = 1 - (0.15f * abs(position))
//        }
//
//        viewPager2.setOffscreenPageLimit(2)
//
//        viewPager2.addItemDecoration(object : RecyclerView.ItemDecoration() {
//            override fun getItemOffsets(
//                outRect: Rect,
//                view: View,
//                parent: RecyclerView,
//                state: RecyclerView.State
//            ) {
//                val position = parent.getChildAdapterPosition(view)
//                if (position == 0) {
//                    outRect.left = 0
//                    outRect.right = largeMargin
//                } else {
//                    outRect.left = 0
//                    outRect.right = largeMargin
//                }
//            }
//        })
//        binding.homViewPagerTeam.adapter = adapterProgress
//
//        val taskAdapter = AdapterTaskHome(emptyList())
//        mainViewModel.listTask.observe(viewLifecycleOwner, { newTaskList ->
//            taskAdapter.updateTasks(newTaskList)
//        })
//
//        binding.homRcTask.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = taskAdapter
//        }
//
//        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                adapterProgress.notifyItemChanged(position)
//                if (position + 1 < goalModel.size) {
//                    adapterProgress.notifyItemChanged(position + 1)
//                }
//                if (position - 1 >= 0) {
//                    adapterProgress.notifyItemChanged(position - 1)
//                }
//            }
//        })
//    }

    private fun setClickListener() {
        binding.homAddTask.setOnClickListener {
            showAddTaskDialog()
        }
//        binding.homTeamBtn.setOnClickListener {
//            binding.homViewPagerTeam.visibility = View.VISIBLE
//            binding.homViewPagerGoal.visibility = View.GONE
//        }
//        binding.homGoalBtn.setOnClickListener {
//            binding.homViewPagerTeam.visibility = View.GONE
//            binding.homViewPagerGoal.visibility = View.VISIBLE
//        }
    }

    private fun setTimeListener() {
        val indonesiaTimeZone = ZoneId.of("Asia/Jakarta")
        val currentDateTimeInIndonesia = ZonedDateTime.now(indonesiaTimeZone)

        // Format nama hari dan bulan dalam bahasa Indonesia
        val dayOfWeek =
            currentDateTimeInIndonesia.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id", "ID"))
        val dayOfMonth = currentDateTimeInIndonesia.dayOfMonth
        val month =
            currentDateTimeInIndonesia.month.getDisplayName(TextStyle.FULL, Locale("id", "ID"))
        val year = currentDateTimeInIndonesia.year

        val formattedDate = "$dayOfWeek, $dayOfMonth $month $year"

        binding.homDate.text = formattedDate;

    }

    private fun showAddTaskDialog() {
        // Inflate layout menggunakan ViewBinding
        val bindingDialog = DlHomeAddtaskBinding.inflate(layoutInflater)

        // Buat AlertDialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(bindingDialog.root)
            .create()
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        bindingDialog.hmaddtaskBtnTask.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tasknewFragment)
            dialog.dismiss()
        }

        bindingDialog.hmaddtaskBtnRepetitive.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_repetitiveFragment)
            dialog.dismiss()
        }

        bindingDialog.hmaddtaskBtnHabit.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_habitFragment)
            dialog.dismiss()
        }

        bindingDialog.hmaddtaskBtnGoal.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_goalFragment)
            dialog.dismiss()
        }

        bindingDialog.hmaddtaskBtnTeam.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_teamTaskFragment)
            dialog.dismiss()
        }

        dialog.show()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}

//            findNavController().navigate(R.id.action_homeFragment_to_newtaskFragment)
//            dialog.dismiss()
//            val bindingDialogLiter = DlLiterationBinding.inflate(layoutInflater)
//
//            // Buat AlertDialog
//            val dialogLiter = AlertDialog.Builder(requireContext())
//                .setView(bindingDialogLiter.root)
//                .create()
//            dialogLiter.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
//            dialogLiter.show()
//            bindingDialogLiter.dlliterBtOk.setOnClickListener {
//                dialogLiter.dismiss()
//            }
//            bindingDialogLiter.dlliterBtCancel.setOnClickListener {
//                dialogLiter.dismiss()
//            }