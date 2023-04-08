import ru.ms.stu.todolist_va.Note
import kotlin.random.Random

class Database private constructor() {
    private var _notes = mutableListOf<Note>()
    val notes: List<Note>
        get() = _notes

    init {
        for (i in 0 until 20) {
            _notes.add(Note(i, "Note $i", Random.nextInt(3)))
        }
    }

    fun add(note: Note) {
        _notes.add(note)
    }

    fun remove(note: Note) {
        _notes.removeAll { it.id == note.id }
    }


    companion object {
        private val instance = Database()

        fun getInstance(): Database {
            return instance
        }
    }
}
