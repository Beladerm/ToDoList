package ru.ms.stu.todolist_va

import Database
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or  ItemTouchHelper.RIGHT) {
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
                database.remove(note.id)
                showNotes()
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
        val notes = database.notes
        notesAdapter.setNotes(notes)
    }

    private fun addNote() {
           binding.buttonAdd.setOnClickListener{
                startActivity(AddNoteActivity.newIntent(this@MainActivity))
            }
        }


}