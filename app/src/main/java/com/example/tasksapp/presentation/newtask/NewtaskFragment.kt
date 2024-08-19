package com.example.tasksapp.presentation.newtask

import android.graphics.Rect
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tasksapp.databinding.FragmentNewtaskBinding
import com.example.tasksapp.presentation.newtask.adapter.CalendarAdapter
import com.example.tasksapp.presentation.newtask.model.CalendarDay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs
import kotlin.math.max

class NewtaskFragment : Fragment() {

    private val viewModel: NewtaskViewModel by viewModels()
    private lateinit var binding: FragmentNewtaskBinding

    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var calendarDays: MutableList<CalendarDay>
    private var calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewtaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupListeners()
        initializeCalendar()

    }

    private fun setupViews() {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        calendarDays = generateDaysForMonth(calendar)
        calendarAdapter = CalendarAdapter(calendarDays, screenWidth) { day ->
            // Handle day click, e.g., show a toast or navigate to another screen
        }
        binding.newtaskVpDays.apply {
            adapter = calendarAdapter
            offscreenPageLimit = 5


            val displayMetrics = resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val itemWidth = screenWidth * 0.2

            val largeMargin = (screenWidth - itemWidth).toInt()

            setPageTransformer { page, position ->
                // Hanya mengatur translasi X, menghilangkan pengaturan skala
                page.setTranslationX((-position * page.width * 4).toFloat())
                // Pengaturan skala dihapus untuk menghindari perkecilan item
            }

            addItemDecoration(object : RecyclerView.ItemDecoration() {
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
        }
//        binding.newtaskVpDays.apply {
//            adapter = calendarAdapter
//            offscreenPageLimit = 3
//
//            val displayMetrics = resources.displayMetrics
//            val screenWidth = displayMetrics.widthPixels
//            val itemWidth = screenWidth * 0.3
//
//            val largeMargin =
//                (screenWidth - itemWidth).toInt()
//            // Set a PageTransformer to create space between items
//            setPageTransformer { page, position ->
//                page.setTranslationX((-position * page.width * 0.3).toFloat())
//                page.setScaleY(1-(0.15f * Math.abs(position)))
////                page.apply {
////                    val scaleFactor = 0.1f.coerceAtLeast(1 - kotlin.math.abs(position))
////                    scaleX = scaleFactor
////                    scaleY = scaleFactor
////                }
//            }

            // Set margins between pages
//            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.space_item_date_1)
//            val offsetPx = resources.getDimensionPixelOffset(R.dimen.space_item_date)
//            setPageTransformer { page, position ->
//                val myOffset = position * -(2 * offsetPx + pageMarginPx)
//                if (ViewPager2.ORIENTATION_HORIZONTAL == orientation) {
//                    if (ViewCompat.getLayoutDirection(this@apply) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                        page.translationX = -myOffset
//                    } else {
//                        page.translationX = myOffset
//                    }
//                } else {
//                    page.translationY = myOffset
//                }
//            }
//        }


//        binding.newtaskRcDays.offscreenPageLimit = 3
//        binding.newtaskRcDays.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//
////        binding.newtaskRcDays.layoutManager =
////            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.newtaskRcDays.adapter = calendarAdapter

        updateMonthTitle()
    }

    private fun setupListeners() {
        binding.newtaskBtnPrevMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendarView()
        }

        binding.newtaskBtnNextMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendarView()
        }
    }

    private fun initializeCalendar() {
        calendarDays = generateDaysForMonth(calendar)
        calendarAdapter.notifyDataSetChanged()
    }

    private fun updateCalendarView() {
        updateMonthTitle()
        calendarDays.clear()
        calendarDays.addAll(generateDaysForMonth(calendar))
        calendarAdapter.notifyDataSetChanged()
    }

    private fun updateMonthTitle() {
        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        binding.newtaskMonthName.text = monthFormat.format(calendar.time)
    }

    private fun generateDaysForMonth(calendar: Calendar): MutableList<CalendarDay> {
        val days = mutableListOf<CalendarDay>()
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        for (i in 1..maxDay) {
            val dayOfWeekFormat = SimpleDateFormat("EEE", Locale.getDefault())
            calendar.set(currentYear, currentMonth, i)
            val dayOfWeek = dayOfWeekFormat.format(calendar.time)
            days.add(CalendarDay(i, currentMonth + 1, currentYear, dayOfWeek))
        }
        return days
    }

    companion object {
        fun newInstance() = NewtaskFragment()
    }
}
