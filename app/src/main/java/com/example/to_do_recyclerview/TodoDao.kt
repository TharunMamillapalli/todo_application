package com.example.to_do_recyclerview

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY id ASC")
    fun getAllTodos(): LiveData<List<Todo>>

    @Insert
    suspend fun insertTodo(todo: Todo):Long

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)
}