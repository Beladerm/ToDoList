package ru.ms.stu.todolist_va

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var noteDatabase = NoteDatabase.getInstance(application)

    fun getNotes(): LiveData<List<Note>> {
        return noteDatabase.notesDao().getNotes()
    }

    fun remove(note: Note) {
        Thread {
            noteDatabase.notesDao().remove(note.id)
        }.start()
    }


}