package com.example.to_do_recyclerview

import android.view.LayoutInflater
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val onCheckboxClicked: (Todo) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val cbDone: CheckBox = itemView.findViewById(R.id.cbDone)
        private var currentTodo: Todo? = null
        init {
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                currentTodo?.let { todo ->
                    val updatedTodo = todo.copy(isChecked = isChecked)
                    onCheckboxClicked(updatedTodo)
                    toggleStrikeThrough(tvTitle, isChecked)
                }
            }
        }

        fun bind(todo: Todo) {
            currentTodo = todo
            tvTitle.text = todo.title

            // Prevent recycling issues
            cbDone.jumpDrawablesToCurrentState()
            cbDone.isChecked = todo.isChecked
            updateStrikeThrough()
        }

        private fun updateStrikeThrough() {
            tvTitle.paintFlags = if (cbDone.isChecked) {
                tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }


        private fun toggleStrikeThrough(textView: TextView, isChecked: Boolean) {
            textView.paintFlags = if (isChecked) {
                textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.isChecked == newItem.isChecked &&
                    oldItem.id == newItem.id
        }
    }
}