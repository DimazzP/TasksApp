package com.example.tasksapp.presentation.newtask

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.databinding.DlPriorityBinding
import com.example.tasksapp.databinding.FragmentTasknewBinding
import com.example.tasksapp.domain.enums.EnumTask
import com.example.tasksapp.domain.model.NewTaskModel
import com.example.tasksapp.domain.model.TaskModel
import com.example.tasksapp.presentation.main.MainViewModel
import com.example.tasksapp.presentation.repetitivetask.adapter.CalendarAdapter
import com.example.tasksapp.presentation.repetitivetask.model.CalendarDay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class TasknewFragment : Fragment() {

    companion object {
        fun newInstance() = TasknewFragment()
    }

    private val viewModel: TasknewViewModel by viewModels()
    private lateinit var binding: FragmentTasknewBinding
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var calendarDays: MutableList<CalendarDay>
    private var calendar: Calendar = Calendar.getInstance()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var startDate: LocalDate = LocalDate.now()
    private var prioritySelected = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasknewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setBottomVisible(false)

        setupViews()
        setupListeners()
        initializeCalendar()
        setupScrollListener()
        setVisibilityView()
    }

    private fun setupViews() {
        val screenWidth = resources.displayMetrics.widthPixels
        calendarDays = generateDaysForMonth(calendar)
        calendarAdapter = CalendarAdapter(calendarDays, screenWidth, binding.newtaskRcDays) { day ->
            startDate = LocalDate.of(day.year, day.month, day.day)
        }

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.newtaskRcDays)
        binding.newtaskRcDays.adapter = calendarAdapter

        lifecycleScope.launch {
            delay(100)
            addPaddingItemDecoration()
            calendarAdapter.setMiddleItemPosition(0)
        }

        updateMonthTitle()
    }

    private fun setupScrollListener() {
        binding.newtaskRcDays.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val middlePosition = getMiddleItemOnScreenPosition(recyclerView)
                    middlePosition?.let { calendarAdapter.setMiddleItemPosition(it) }
                }
            }
        })
    }

    private fun getMiddleItemOnScreenPosition(recyclerView: RecyclerView): Int? {

        val screenCenter = recyclerView.width / 2
        var closestPosition: Int? = null
        var minDistance = Int.MAX_VALUE

        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)
            val childCenter = (child.left + child.right) / 2
            val distanceToCenter = Math.abs(childCenter - screenCenter)

            if (distanceToCenter < minDistance) {
                minDistance = distanceToCenter
                closestPosition = recyclerView.getChildAdapterPosition(child)
            }
        }

        return closestPosition
    }

    private fun addPaddingItemDecoration() {
        val screenWidth = resources.displayMetrics.widthPixels
        binding.newtaskRcDays.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val itemCount = state.itemCount
                val itemWidth = getItemWidths(binding.newtaskRcDays)
                var paddingLeft = 0
                var paddingRight = 0

                if (position == 0 && itemWidth != null) {
                    paddingLeft = (screenWidth / 2) - (itemWidth / 2)
                    outRect.left = paddingLeft
                }

                if (position == itemCount - 1 && itemWidth != null) {
                    paddingRight = (screenWidth / 2) - (itemWidth / 2.5).toInt()
                    Log.d("NewtaskFragment", "paddingRight: $paddingRight")
                    outRect.right = paddingRight
                }
            }
        })
    }

    private fun refreshItemDecoration() {
        binding.newtaskRcDays.removeItemDecorationAt(0)
        binding.newtaskRcDays.invalidateItemDecorations()
    }

    private fun getItemWidths(recyclerView: RecyclerView): Int? {
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
        val firstVisibleItemPosition = layoutManager?.findFirstVisibleItemPosition()

        return if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
            firstVisibleItemPosition?.let { layoutManager.findViewByPosition(it)?.width }
        } else {
            null
        }
    }

    private fun setVisibilityView() {
        binding.apply {
            newtaskMoreBottom.setOnClickListener {
                if (newtaskNextConst.visibility == View.GONE) {
                    newtaskNextConst.visibility = View.VISIBLE
                    newtaskMoreBottom.setImageResource(R.drawable.ic_ios_arrow_top)
                } else {
                    newtaskNextConst.visibility = View.GONE
                    newtaskMoreBottom.setImageResource(R.drawable.ic_ios_arrow_bottom)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.newtaskBtnPrevMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendarView()
        }

        binding.newtaskMore.setOnClickListener {
            createNewTask()
            findNavController().popBackStack()
        }

        binding.newtaskBtnNextMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendarView()
        }

        binding.newtaskBtAddPriority.setOnClickListener {
            val bindingPrio = DlPriorityBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(bindingPrio.root)

            val dialog = builder.create()
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            bindingPrio.dlprioBtCancel.setOnClickListener {
                dialog.dismiss()
            }

            bindingPrio.dlprioBtOk.setOnClickListener {
                prioritySelected = bindingPrio.dlprioNumberPriority.text.toString().toInt()
                dialog.dismiss()
            }

            bindingPrio.dlprioBtMinus.setOnClickListener {
                val currentValue = bindingPrio.dlprioNumberPriority.text.toString().toInt()
                if (currentValue > 0) {
                    bindingPrio.dlprioNumberPriority.text = (currentValue - 1).toString()
                }
            }

            bindingPrio.dlprioBtPlus.setOnClickListener {
                val currentValue = bindingPrio.dlprioNumberPriority.text.toString().toInt()
                bindingPrio.dlprioNumberPriority.text = (currentValue + 1).toString()
            }
            dialog.show()
        }

        binding.newtaskBtnback.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initializeCalendar() {
        calendarDays = generateDaysForMonth(calendar)
        calendarAdapter.notifyDataSetChanged()
    }

    private fun updateCalendarView() {
        val newDays = generateDaysForMonth(calendar)
        calendarAdapter.updateDays(newDays)
        binding.newtaskRcDays.scrollToPosition(0)
        updateMonthTitle()
        calendarAdapter.setMiddleItemPosition(0)
    }

    private fun updateMonthTitle() {
        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale("in", "ID"))
        binding.newtaskMonthName.text = monthFormat.format(calendar.time)
    }

    private fun generateDaysForMonth(calendar: Calendar): MutableList<CalendarDay> {
        val days = mutableListOf<CalendarDay>()
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)
        val dayOfWeekFormat = SimpleDateFormat("EEE", Locale("in", "ID"))

        for (i in 1..maxDay) {
            calendar.set(currentYear, currentMonth, i)
            val dayOfWeek = dayOfWeekFormat.format(calendar.time)
            days.add(CalendarDay(i, currentMonth + 1, currentYear, dayOfWeek))
        }
        return days
    }

    private fun createNewTask() {
        binding.apply {
            if (newtaskTitleEdit.text == null || newtaskTitleEdit.text?.isEmpty() == true) {
                Toast.makeText(
                    requireContext(),
                    "Nama Tugas Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            var startLocal: LocalDateTime? = null
            if (startDate != null) {
                startLocal = startDate.atStartOfDay()
                    .withHour(
                        newtaskCustomtimepicker.getHour()
                    ).withMinute(newtaskCustomtimepicker.getMinute())
            }

            val lastIdNewTask = mainViewModel.listNewTask.value?.last()
            val finalIdNewTask = lastIdNewTask?.id?.plus(1) ?: 1
            val lastTaskModel = mainViewModel.listTask.value?.last()
            val finalIdTaskModel = lastTaskModel?.idTask?.plus(1) ?: 1

            val newTask = NewTaskModel(
                id = finalIdNewTask,
                title = newtaskTitleEdit.text.toString(),
                description = newtaskDescription.text.toString(),
                subTask = null,
                startDate = startLocal!!,
                postpone = newtaskCheckPostpone.isChecked,
                priority = prioritySelected,
                reminder = 0
            )
            mainViewModel.addNewTask(newTask)
            mainViewModel.addTaskModel(
                TaskModel(
                    idTask = finalIdTaskModel,
                    subTask = null,
                    enumTask = EnumTask.NEW,
                    idKeyTask = finalIdNewTask,
                    titleTask = newtaskTitleEdit.text.toString(),
                    time = newtaskCustomtimepicker.getHour()
                        .toString() + ":" + newtaskCustomtimepicker.getMinute(),
                    startDate = startLocal,
                    repetitive = false,
                    teams = emptyList()
                )
            )
        }
    }
}