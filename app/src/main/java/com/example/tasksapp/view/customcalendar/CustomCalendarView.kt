package com.example.tasksapp.view.customcalendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var calendar: Calendar = Calendar.getInstance(Locale.getDefault())
    private lateinit var adapter: CalendarAdapterAdapt
    private val monthYearTextView: TextView
    private val recyclerView: RecyclerView
    private val prevMonthButton: ImageButton
    private val nextMonthButton: ImageButton
    var onDateSelected: (LocalDateTime) -> Unit = {}

    private val notesMap = mutableMapOf<String, String?>()
    private var days = generateCalendarDataAdapt()
    private var selectedDate: LocalDateTime = LocalDateTime.now()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_calendar, this, true)
        monthYearTextView = findViewById(R.id.calend_month)
        recyclerView = findViewById(R.id.recyclerView)
        prevMonthButton = findViewById(R.id.ic_prev_month)
        nextMonthButton = findViewById(R.id.ic_next_month)

        setupRecyclerView()
        updateMonthYearText()
        loadCalendarData()

        prevMonthButton.setOnClickListener {
            changeMonth(-1)
        }

        nextMonthButton.setOnClickListener {
            changeMonth(1)
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, 7)
    }

    private fun loadCalendarData() {
        adapter = CalendarAdapterAdapt(days, calendar) { day, month, year ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, day)
            selectedDate =
                selectedCalendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            onDateSelected(selectedDate)

        }
        recyclerView.adapter = adapter

    }

    fun getSelectedDateLocalDate(): LocalDateTime {
        val calendar = Calendar.getInstance()
        calendar.set(
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth
        ) // Perhatikan bahwa bulan di Calendar dimulai dari 0
        // Konversi Calendar ke LocalDateTime dan set jam ke 7:00
        return calendar.time.toInstant().atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .withHour(7)    // Set jam ke 7
            .withMinute(0)  // Set menit ke 0
            .withSecond(0)  // Set detik ke 0
            .withNano(0)    // Set nano detik ke 0
    }

    private fun changeMonth(amount: Int) {
        calendar.add(Calendar.MONTH, amount)
        updateMonthYearText()
        days = generateCalendarDataAdapt()
        loadCalendarData()
    }

    private fun updateMonthYearText() {
        val indonesianLocale = Locale("in", "ID")
        val dateFormat = SimpleDateFormat("MMMM yyyy", indonesianLocale)
        val formattedDate = dateFormat.format(calendar.time)
        monthYearTextView.text = formattedDate
    }

    private fun generateCalendarDataAdapt(): List<CalendarDayAdapt> {
        val days = mutableListOf<CalendarDayAdapt>()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Tambahkan placeholder untuk hari-hari kosong sebelum hari pertama dalam minggu
        for (i in 1 until firstDayOfMonth) {
            days.add(
                CalendarDayAdapt(
                    0,
                    null,
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR)
                )
            )
        }

        // Tambahkan hari-hari dalam bulan ini dengan catatan yang disimpan (jika ada)
        for (day in 1..maxDay) {
            val key = "$day-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.YEAR)}"
            val note = notesMap[key]
            days.add(
                CalendarDayAdapt(
                    day,
                    note,
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR)
                )
            )
        }

        return days
    }

    fun setNoteForDate(day: Int, note: String?, month: Int, year: Int) {
        val key = "$day-$month-$year"
        notesMap[key] = note

        days.find { it.day == day && it.month == month && it.year == year }?.notes = note
        adapter.notifyDataSetChanged()
    }

    fun getCurrentMonth(): Int {
        return calendar.get(Calendar.MONTH)
    }

    fun getCurrentYear(): Int {
        return calendar.get(Calendar.YEAR)
    }
}
