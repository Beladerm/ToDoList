package ru.ms.stu.todolist_va

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val notesDao = NoteDatabase.getInstance(application).notesDao()
    private var shouldCloseScreen = MutableLiveData<Boolean>()
    private var compositeDisposable = CompositeDisposable()

    fun getShouldCloseScreen(): LiveData<Boolean> = shouldCloseScreen

    fun saveNote(note: Note) {
        val disposable = notesDao.addNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                shouldCloseScreen.value = true
            }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}