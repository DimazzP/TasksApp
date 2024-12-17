package com.example.tasksapp.domain.model

import com.example.tasksapp.domain.model.utils.ActivityRest
import java.time.LocalDateTime

data class NewTaskModel(
    val id: Int,
    val title: String,
    val description: String?,
    val subTask: List<String>?,
    val startDate: LocalDateTime,
    val priority: Int=0,
    val reminder: Int=0,
    val postpone: Boolean = false
);