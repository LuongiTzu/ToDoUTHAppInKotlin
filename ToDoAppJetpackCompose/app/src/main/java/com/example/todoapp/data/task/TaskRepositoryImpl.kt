package com.example.todoapp.data.task

import com.example.todoapp.domain.task.model.TaskResponse
import com.example.todoapp.domain.task.model.BaseResponse
import com.example.todoapp.domain.task.repository.TaskRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TaskRepositoryImpl : TaskRepository {

    private val api: TaskApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://amock.io/api/researchUTH/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApiService::class.java)
    }

    override suspend fun getTasks(): BaseResponse<List<TaskResponse>> = api.getTasks()

    override suspend fun getTaskById(id: Int): BaseResponse<TaskResponse> = api.getTaskById(id)

    override suspend fun deleteTask(id: Int): BaseResponse<Unit> = api.deleteTask(id)
}
