package ru.ms.stu.todolist_va

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.ms.stu.todolist_va.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private var viewModel: AddNoteViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater).also { setContentView(it.root) }

        viewModel = ViewModelProvider(this)[AddNoteViewModel::class.java]
        viewModel?.getShouldCloseScreen()?.observe(this) {
            if (it == true) {
                finish()
            }
        }

        saveNote()
    }

    private fun saveNote() {
        with(binding) {
            save.setOnClickListener {
                if (label.text.isBlank()) {
                    Toast.makeText(
                        this@AddNoteActivity,
                        "Your must write a text!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val text = label.text.toString().trim()
                    val priority = getPriority()
                    viewModel?.saveNote(Note(text, priority))

                }
            }
        }
    }

    private fun getPriority(): Int {
        var priority: Int
        with(binding) {
            priority = if (checkboxLow.isChecked) {
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