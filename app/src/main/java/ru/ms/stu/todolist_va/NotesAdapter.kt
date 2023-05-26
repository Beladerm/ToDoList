package ru.ms.stu.todolist_va

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ms.stu.todolist_va.databinding.NoteItemBinding

class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var _notes = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = _notes[position]
        holder.bind(note)
        holder.itemView.setOnClickListener {
            
        }
    }

    override fun getItemCount(): Int {
        return _notes.size
    }

    fun setNotes(notes: List<Note>) {
        _notes.clear()
        _notes.addAll(notes)
        notifyDataSetChanged()
    }


    class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            with(binding.textViewNote) {
                text = note.text
                setBackgroundColor(
                    when (note.priority) {
                        0 -> Color.GREEN
                        1 -> Color.rgb(255, 162, 0) // ORANGE
                        else -> Color.RED
                    }
                )
            }
        }
    }

}
