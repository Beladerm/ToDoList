package ru.ms.stu.todolist_va

import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ms.stu.todolist_va.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter

    private var noteDatabase : NoteDatabase? = null
    private val handler = android.os.Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        noteDatabase = NoteDatabase.getInstance(application)

        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter()
        notesAdapter.setOnNoteClickListener(object : NotesAdapter.OnNoteClickListener {
           override fun onNoteClick(note: Note) {
               Thread {
                   kotlin.run {
                       noteDatabase?.notesDao()?.remove(note.id)
                   }
                   handler.post {
                       showNotes()
                   }
               }.start()
           }
        })
        binding.recyclerViewNotes.adapter = notesAdapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = notesAdapter.getNotes()[position]

                Thread {
                    kotlin.run {
                        noteDatabase?.notesDao()?.remove(note.id)
                        handler.post{
                            showNotes()
                        }
                    }
                }.start()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)

        addNote()
    }

    override fun onResume() {
        super.onResume()
        showNotes()

    }

    private fun showNotes() {
        Thread {
            kotlin.run {
                val notes = noteDatabase?.notesDao()?.getNotes()
                handler.post{
                    notes?.let { notesAdapter.setNotes(it) }
                }

            }
        }.start()

    }

    private fun addNote() {
           binding.buttonAdd.setOnClickListener{
                startActivity(AddNoteActivity.newIntent(this@MainActivity))
            }
        }


}