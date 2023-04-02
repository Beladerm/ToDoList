package ru.ms.stu.todolist_va

import Database
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.ms.stu.todolist_va.databinding.ActivityMainBinding
import ru.ms.stu.todolist_va.databinding.NoteItemBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val database = Database.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        addNote()
    }

    override fun onResume() {
        super.onResume()
        showNotes()

    }

    private fun showNotes() {
        binding.linearLayout.removeAllViews()
        for (note in database.notes) {
            val noteItemBinding = NoteItemBinding.inflate(
                layoutInflater,
                binding.linearLayout,
                false
            )
            noteItemBinding.textViewNote.text = note.text
            noteItemBinding.textViewNote.setBackgroundColor(
                when (note.priority) {
                    0 -> Color.GREEN
                    1 -> Color.rgb(255, 162, 0) // ORANGE
                    else -> Color.RED // else == 2
                }
            )
            binding.linearLayout.addView(noteItemBinding.root)
        }
    }

    private fun addNote() {
        with(binding) {
            buttonAdd.setOnClickListener{
                intent = AddNoteActivity.newIntent(this@MainActivity)
                startActivity(intent)
            }
        }
    }
}