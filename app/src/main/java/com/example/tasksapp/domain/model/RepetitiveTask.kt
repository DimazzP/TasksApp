package com.example.tasksapp.domain.model

import com.example.tasksapp.domain.model.utils.ActivityRest
import java.util.Date

data class RepetitiveTask(
    val idRepetitiveTask: Int,
    val selectedCycle: Int=0,
    val everyDay: Boolean?,
    val everyWeek: List<Int>?,
    val everyMonth: List<Int>?,
    val everyYear: Int?,
    val restActivityCycle: ActivityRest?,
    val startDate: Date = Date(),
    val endDate: Date?,
    val remember: Int = 0,
    val priority: Int = 0
)