package com.example.tasksapp.domain.model

import com.example.tasksapp.domain.enums.EnumTask

data class TaskModel (
    val idTask: Int,
    val enumTask: EnumTask,
    val idKeyTask: Int,
    val titleTask: String,
    val time: String?,
    val repetitive: Boolean?,
    val teams: List<UserProfileModel>?
)