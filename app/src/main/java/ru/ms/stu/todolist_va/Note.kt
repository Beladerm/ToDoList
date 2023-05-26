package ru.ms.stu.todolist_va

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "notes")

class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val priority: Int
    ) {
    @Ignore
    constructor(text: String, priority: Int) : this(0, text, priority)
}