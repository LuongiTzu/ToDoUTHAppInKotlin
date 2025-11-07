package com.example.todoapp.domain.task.usecase

import com.example.todoapp.data.task.TaskRepositoryImpl

class GetTaskDetailUseCase {
    suspend operator fun invoke(id: Int) = TaskRepositoryImpl.getTaskById(id)
}
