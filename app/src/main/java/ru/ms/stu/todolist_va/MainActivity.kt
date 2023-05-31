package ru.ms.stu.todolist_va

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ms.stu.todolist_va.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var noteLiveData: LiveData<List<Note>>

    private var noteDatabase : NoteDatabase? = null
    private val notesObserver = Observer<List<Note>> {
        notes -> notes?.let { notesAdapter.setNotes(it)}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        noteDatabase = NoteDatabase.getInstance(application)
        val noteDao = noteDatabase?.notesDao()
        noteLiveData = noteDao?.getNotes() ?: MutableLiveData()

        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter()
        binding.recyclerViewNotes.adapter = notesAdapter
        noteLiveData.observe(this, notesObserver)

        notesAdapter.setOnNoteClickListener(object : NotesAdapter.OnNoteClickListener {
           override fun onNoteClick(note: Note) {
               Thread {
                       noteDatabase?.notesDao()?.remove(note.id)
               }.start()
           }
        })

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
                        noteDatabase?.notesDao()?.remove(note.id)
                }.start()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)

        addNote()
    }


    private fun addNote() {
           binding.buttonAdd.setOnClickListener{
                startActivity(AddNoteActivity.newIntent(this@MainActivity))
            }
        }


}