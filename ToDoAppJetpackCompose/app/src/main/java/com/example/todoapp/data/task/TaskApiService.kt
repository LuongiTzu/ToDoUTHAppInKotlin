package com.example.todoapp.data.task

import com.example.todoapp.domain.task.model.BaseResponse
import com.example.todoapp.domain.task.model.TaskResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskApiService {

    @GET("tasks")
    suspend fun getTasks(): BaseResponse<List<TaskResponse>>

    @GET("task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): BaseResponse<TaskResponse>

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") id: Int): BaseResponse<Unit>

}
