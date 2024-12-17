package com.example.tasksapp.domain.model

import com.example.tasksapp.domain.enums.EnumTask
import java.time.LocalDateTime

data class TaskModel (
    val idTask: Int,
    val enumTask: EnumTask,
    val idKeyTask: Int,
    val titleTask: String,
    val time: String?,
    val repetitive: Boolean?,
    val startDate: LocalDateTime?,
    val teams: List<UserProfileModel>?
)