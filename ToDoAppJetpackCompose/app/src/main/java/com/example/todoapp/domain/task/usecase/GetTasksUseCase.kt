package com.example.todoapp.domain.task.usecase

import com.example.todoapp.data.task.TaskRepositoryImpl

class GetTasksUseCase {
    suspend operator fun invoke() = TaskRepositoryImpl.getTasks()
}
