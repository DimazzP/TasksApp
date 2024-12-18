package com.example.tasksapp.domain.model

import java.time.LocalDateTime

data class GoalModel(
    val id: Int,
    val title: String,
    val description: String?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime?,
    val remember: Int = 0,
    val priority: Int = 0,
    val goalTarget: List<GoalTarget>?,
    val progress: Int = 0,
    val teams: List<UserProfileModel>?
)

data class GoalTarget(
    val selectFreq: Int,
    val interval: GoalInterval?,
    val alreadyFinish: Boolean?,
    val currency: GoalInterval?
)

data class GoalInterval(
    val start: Int,
    val end: Int,
)