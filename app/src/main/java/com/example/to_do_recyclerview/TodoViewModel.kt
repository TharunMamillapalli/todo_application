package com.example.to_do_recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TodoViewModel(private val todoDao: TodoDao) : ViewModel() {
    val todos: LiveData<List<Todo>> = todoDao.getAllTodos()

    fun insertTodo(title: String) {
        viewModelScope.launch {
            todoDao.insertTodo(Todo(title = title, isChecked = false))
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoDao.deleteTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            todoDao.updateTodo(todo)
        }
    }
}