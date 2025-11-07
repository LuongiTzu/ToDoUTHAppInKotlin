package com.example.todoapp.domain.task.usecase

import com.example.todoapp.data.task.TaskRepositoryImpl

class DeleteTaskUseCase {
    suspend operator fun invoke(id: Int) = TaskRepositoryImpl.deleteTask(id)
}
