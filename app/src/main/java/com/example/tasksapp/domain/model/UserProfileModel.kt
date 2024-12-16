package com.example.tasksapp.domain.model

data class UserProfileModel(
    val name: String,
    val userName: String,
    val email: String,
    val profileImage: String?,
    val telephone: String?,
)