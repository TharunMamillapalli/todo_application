package com.example.to_do_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database and DAO
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo-database"
        ).build()
        val todoDao = database.todoDao()

        // Initialize ViewModel
        viewModel = ViewModelProvider(
            this,
            TodoViewModelFactory(database.todoDao())
        )[TodoViewModel::class.java]

        // Setup RecyclerView
        val rvTodos = findViewById<RecyclerView>(R.id.rvTodos)
        adapter = TodoAdapter { updatedTodo ->
            viewModel.updateTodo(updatedTodo)
        }
        rvTodos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            itemAnimator = null  // Disable animations to prevent flickering
        }

        // Observe todos from ViewModel
        viewModel.todos.observe(this) { todos ->
            adapter.submitList(todos.toList())
        }

        // Swipe to delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val todo = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteTodo(todo)
            }
        }).attachToRecyclerView(rvTodos)

        // Add new todo
        val etTodo = findViewById<EditText>(R.id.etTodo)
        findViewById<Button>(R.id.btnTodo).setOnClickListener {
            val title = etTodo.text.toString().trim()
            if (title.isNotEmpty()) {
                viewModel.insertTodo(title)
                etTodo.text.clear()
            }
        }
    }
}