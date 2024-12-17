package com.example.tasksapp.domain.model

import com.example.tasksapp.domain.model.utils.ActivityRest
import java.time.LocalDate
import java.util.Date

data class HabitsTaskModel(
    val id: Int,
    val title: String,
    val description: String?,
    val evaluation: Int=0,
    val frequencyWork: Int = 0,
    val evaluationNumeric: Int?,
    val frequencyTask : Int=0,
    val frequencyEveryWeek: List<Int>?,
    val frequencyActivityRest: ActivityRest?,
    val startDate: LocalDate= LocalDate.now(),
    val endDate: LocalDate?,
    val remember: Int=0,
    val priority: Int=0,
)