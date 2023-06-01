package ru.ms.stu.todolist_va

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ms.stu.todolist_va.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var noteLiveData: LiveData<List<Note>>

    private var viewModel: MainViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        noteLiveData = viewModel?.getNotes() ?: MutableLiveData()

        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter()
        binding.recyclerViewNotes.adapter = notesAdapter
        noteLiveData.observe(this) { notes ->
            notes?.let { notesAdapter.setNotes(it) }
        }

        notesAdapter.setOnNoteClickListener(object : NotesAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note) {
                //viewModel?.remove(note)

            }
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = notesAdapter.getNotes()[position]
                viewModel?.remove(note)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)

        addNote()
    }


    private fun addNote() {
        binding.buttonAdd.setOnClickListener {
            startActivity(AddNoteActivity.newIntent(this@MainActivity))
        }
    }


}