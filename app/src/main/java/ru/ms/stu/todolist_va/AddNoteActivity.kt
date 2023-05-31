package ru.ms.stu.todolist_va

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.ms.stu.todolist_va.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private var noteDatabase : NoteDatabase? = null

    private val handler = android.os.Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater).also { setContentView(it.root) }

        noteDatabase = NoteDatabase.getInstance(application)

        saveNote()
    }

    private fun saveNote() {
        with(binding) {
            save.setOnClickListener {
                if (label.text.isEmpty()) {
                    Toast.makeText(
                        this@AddNoteActivity,
                        "Your must write a text!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val text = label.text.toString().trim()
                    val priority = getPriority()
                    Thread {
                            noteDatabase?.notesDao()?.addNote(Note(text, priority))
                            handler.post {
                                finish()
                        }
                    }.start()
                }
            }
        }
    }

    private fun getPriority(): Int {
        var priority: Int
        with(binding) {
            priority = if(checkboxLow.isChecked) {
                0
            } else if (checkboxMedium.isChecked) {
                1
            } else 2

        }
        return priority
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNoteActivity::class.java)
        }
    }
}