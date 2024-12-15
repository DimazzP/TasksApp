package com.example.tasksapp.data.model

data class UserProfileModel(
    val name: String,
    val userName: String,
    val email: String,
    val profileImage: ByteArray?,
    val telephone: String?,
)