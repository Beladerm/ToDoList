package ru.ms.stu.todolist_va

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var noteDatabase = NoteDatabase.getInstance(application)
    private var compositeDisposable = CompositeDisposable()
    private var notes = MutableLiveData<List<Note>>()

    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

     fun refresh() {
        val disposable = noteDatabase.notesDao().getNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                notes.value = it
            })
        compositeDisposable.add(disposable)
    }

    fun remove(note: Note) {
        val disposable = noteDatabase.notesDao().remove(note.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("MainViewModel", "remove " + note.id)
                refresh()
                }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}