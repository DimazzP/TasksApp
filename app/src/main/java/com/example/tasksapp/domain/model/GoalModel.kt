package com.example.tasksapp.domain.model

import java.time.LocalDateTime

data class GoalModel(
    val id: Int,
    val title: String,
    val description: String?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime?,
    val remember: Int = 0,
    val priority: Int = 0
)