package ru.ms.stu.todolist_va

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var noteDatabase = NoteDatabase.getInstance(application)
    private var compositeDisposable = CompositeDisposable()


    fun getNotes(): LiveData<List<Note>> {
        return noteDatabase.notesDao().getNotes()
    }


    fun remove(note: Note) {
        val disposable = noteDatabase.notesDao().remove(note.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("MainViewModel", "remove " + note.id)
                },
                {
                    Log.d("MainViewModel", "remove error")
                })
        compositeDisposable.add(disposable)
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

// private var notes = MutableLiveData<List<Note>>()
//    private fun getNotesRx() : Single<List<Note>> {
//        return Single.fromCallable(Callable {
//            return@Callable noteDatabase.notesDao().getNotes()
//            throw Exception("Error")
//        } )
//    }
//     fun refresh() {
//        val disposable = noteDatabase.notesDao().getNotes()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    notes.value = it
//                }, {
//                    Log.d("MainViewModel", "refresh error")
//            })
//        compositeDisposable.add(disposable)
//    }
//    private fun removeRx(note: Note): Completable {
////        return Completable.fromAction{
////            noteDatabase.notesDao().remove(note.id)
////            throw Exception("Error")
////        }
////    }