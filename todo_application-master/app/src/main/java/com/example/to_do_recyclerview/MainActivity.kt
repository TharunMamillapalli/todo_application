package com.example.to_do_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var etTodo=findViewById<EditText>(R.id.etTodo)
        var btnTodo=findViewById<Button>(R.id.btnTodo)
        var rvTodos=findViewById<RecyclerView>(R.id.rvTodos)
        var todoList= mutableListOf(
            Todo("Follow Philipp lackner's android tutorials",true),
            Todo("Learn about Recycler View",true),
            Todo("Take a shower",false),
            Todo("Eat some curry",false)
        )
        val adapter=TodoAdapter(todoList)
        rvTodos.adapter=adapter
        rvTodos.layoutManager=LinearLayoutManager(this)
        btnTodo.setOnClickListener(){
            var title=etTodo.text.toString()
            val todo=Todo(title,false)
            todoList.add(todo)
            adapter.notifyItemChanged(todoList.size-1)
        }

    }
}