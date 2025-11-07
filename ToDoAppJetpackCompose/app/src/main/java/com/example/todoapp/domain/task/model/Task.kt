package com.example.todoapp.domain.task.model

data class BaseResponse<T>(
    val isSuccess: Boolean,
    val message: String,
    val data: T
)

data class TaskResponse(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val category: String,
    val dueDate: String,
    val subtasks: List<Subtask> = emptyList(),
    val attachments: List<Attachment> = emptyList()
)

data class Subtask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean
)

data class Attachment(
    val id: Int,
    val fileName: String,
    val fileUrl: String
)
