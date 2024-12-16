package com.example.tasksapp.domain.model

import com.example.tasksapp.domain.model.utils.ActivityRest
import java.time.LocalDateTime

data class NewTaskModel(
    val title: String,
    val description: String?,
    val subTask: List<String>?,
    val freqTask: Int = 0,
    val freqWeekly: List<Int>?,
    val freqMontly: List<Int>?,
    val freqYearly: Int?,
    val freqActivityRest: ActivityRest?,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime?,
    val reminder: Int = 0,
    val priority: Int = 0,
    val postpone: Boolean = false,
);