package com.example.to_do_recyclerview

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo (
    var title:String,
    var isChecked:Boolean,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
