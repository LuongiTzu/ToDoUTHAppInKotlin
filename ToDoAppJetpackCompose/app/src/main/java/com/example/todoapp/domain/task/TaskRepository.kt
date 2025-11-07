package com.example.todoapp.domain.task.repository

import com.example.todoapp.domain.task.model.*

interface TaskRepository {
    suspend fun getTasks(): BaseResponse<List<TaskResponse>>
    suspend fun getTaskById(id: Int): BaseResponse<TaskResponse>
    suspend fun deleteTask(id: Int): BaseResponse<Unit>
}
