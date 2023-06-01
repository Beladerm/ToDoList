package ru.ms.stu.todolist_va

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val notesDao = NoteDatabase.getInstance(application).notesDao()
    private var shouldCloseScreen = MutableLiveData<Boolean>()

    fun getShouldCloseScreen(): LiveData<Boolean> = shouldCloseScreen

    fun saveNote(note: Note) {
        Thread {
            notesDao.addNote(note)
            shouldCloseScreen.postValue(true)
        }.start()
    }
}