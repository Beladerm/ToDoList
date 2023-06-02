package ru.ms.stu.todolist_va

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): Single<List<Note>>

    @Insert
    fun addNote(note: Note) : Completable

    @Query("DELETE FROM notes WHERE id = :id")
    fun remove(id: Int) : Completable
}