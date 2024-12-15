package com.example.tasksapp.data.model

import com.example.tasksapp.data.enum.EnumTask

data class TaskModel (
    val idTask: Int,
    val enumTask: EnumTask,
    val idKeyTask: Int,

)