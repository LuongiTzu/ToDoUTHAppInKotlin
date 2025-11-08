package com.example.todoapp.feature.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.task.model.TaskResponse
import com.example.todoapp.domain.task.usecase.DeleteTaskUseCase
import com.example.todoapp.domain.task.usecase.GetTaskDetailUseCase
import com.example.todoapp.domain.task.usecase.GetTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val getTasksUseCase = GetTasksUseCase()
    private val getTaskDetailUseCase = GetTaskDetailUseCase()
    private val deleteTaskUseCase = DeleteTaskUseCase()

    private val _tasks = MutableStateFlow<List<TaskResponse>>(emptyList())
    val tasks: StateFlow<List<TaskResponse>> = _tasks

    private val _task = MutableStateFlow<TaskResponse?>(null)
    val task: StateFlow<TaskResponse?> = _task

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _shouldReload = MutableStateFlow(false)
    val shouldReload: StateFlow<Boolean> = _shouldReload

    fun loadTasks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = getTasksUseCase()
                if (response.isSuccess) _tasks.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
                _tasks.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadDetail(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = getTaskDetailUseCase(id)
                if (response.isSuccess) _task.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeTask(id: Int, onDone: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = deleteTaskUseCase(id)
                if (response.isSuccess) {
                    _shouldReload.value = true     // ✅ báo cho TaskList reload
                    onDone()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetReloadFlag() {
        _shouldReload.value = false
    }
}
