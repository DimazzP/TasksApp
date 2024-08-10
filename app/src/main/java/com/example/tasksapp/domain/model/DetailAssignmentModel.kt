package com.example.tasksapp.domain.model

import java.time.LocalDateTime

data class DetailAssignmentModel(
    val name: String,
    val time: LocalDateTime,
    val isChecked: Boolean,
)
