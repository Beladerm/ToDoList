package ru.ms.stu.todolist_va

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    companion object {
        private var instance: NoteDatabase? = null
        private const val DB_NAME = "notes.db"

        fun getInstance(context: Context): NoteDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance as NoteDatabase
        }
    }

    abstract fun notesDao(): NotesDao

}