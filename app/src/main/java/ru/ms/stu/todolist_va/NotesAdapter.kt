package ru.ms.stu.todolist_va

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ms.stu.todolist_va.databinding.NoteItemBinding

class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var _notes = mutableListOf<Note>()
    private var onNoteClickListener: OnNoteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = _notes[position]
        holder.bind(note)
        holder.itemView.setOnClickListener {
            onNoteClickListener?.onNoteClick(note)
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

    fun getNotes(): List<Note> {
        return _notes
    }

    fun setOnNoteClickListener(onNoteClickListener: OnNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener
    }


    class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            with(binding.textViewNote ) {
                text = note.text
                setBackgroundColor(
                    when (note.priority) {
                        0 -> Color.rgb(154, 205, 50) // GREEN
                        1 -> Color.rgb(255, 162, 0) // ORANGE
                        else -> Color.rgb(178, 34, 34) // RED
                    }
                )
//                changeColor(note)
            }
        }
        private fun changeColor(note: Note) {
            with(binding.cardViewNote) {
                setBackgroundColor(
                    when (note.priority) {
                        0 -> Color.rgb(154, 205, 50) // GREEN
                        1 -> Color.rgb(255, 162, 0) // ORANGE
                        else -> Color.rgb(178, 34, 34) // RED
                    }
                )
            }
        }
    }



    interface OnNoteClickListener {
        fun onNoteClick(note: Note)
    }

}
