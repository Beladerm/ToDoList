package ru.ms.stu.todolist_va

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val notesDao = NoteDatabase.getInstance(application).notesDao()
    private var shouldCloseScreen = MutableLiveData<Boolean>()
    private var compositeDisposable = CompositeDisposable()

    fun getShouldCloseScreen(): LiveData<Boolean> = shouldCloseScreen

    fun saveNote(note: Note) {
        val disposable =  notesDao.addNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                shouldCloseScreen.value = true
            }, {
               Log.d("test", "error")
            })
        compositeDisposable.add(disposable)
    }

//    private fun addNoteRx(note: Note): Completable {
//        return Completable.fromAction{
//            notesDao.addNote(note)
//            throw Exception("test")
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}