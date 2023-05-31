package ru.ms.stu.todolist_va

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Note>>

    @Insert
    fun addNote(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    fun remove(id: Int)
}