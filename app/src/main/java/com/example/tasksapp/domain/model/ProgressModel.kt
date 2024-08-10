package com.example.tasksapp.domain.model

data class ProgressModel(
    val title: String,
    val progress: Int,
    val member: List<MemberModel>,
    val detailAssignment: List<DetailAssignmentModel>
)