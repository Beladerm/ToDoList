package ru.ms.stu.todolist_va

import Database
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ms.stu.todolist_va.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter

    private val database = Database.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter()
        notesAdapter.setOnNoteClickListener(object : NotesAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note) {
                database.remove(note.id)
                showNotes()
            }
        })

        binding.recyclerViewNotes.adapter = notesAdapter

        addNote()
    }

    override fun onResume() {
        super.onResume()
        showNotes()

    }

    private fun showNotes() {
        val notes = database.notes
        notesAdapter.setNotes(notes)
    }

    private fun addNote() {
           binding.buttonAdd.setOnClickListener{
                startActivity(AddNoteActivity.newIntent(this@MainActivity))
            }
        }


}