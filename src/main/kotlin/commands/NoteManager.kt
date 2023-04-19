package commands
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.File

class NoteManager() {
    private var mapper = ObjectMapper()
    private val file = File("src/main/data/notes.json");
    private var root: ObjectNode = this.mapper.readValue(file, ObjectNode::class.java)
    fun write(title: String, body: String){
        val notesArray = root.get("notes") as ArrayNode
        val newNote = mapper.createObjectNode()
        newNote.put("title", title)
        newNote.put("body", body)
        notesArray.add(newNote)

        val amount = root.get("amount").asInt() + 1
        root.put("amount", amount)

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, root)
    }

    fun read(amount: Int) {
        val amountOfNotes = root.get("amount").asInt()
        var finalAmount = amount;
        if(amount == -1){
            finalAmount = amountOfNotes
        }
        if (finalAmount > amountOfNotes) {
            throw error("Not enough notes to return.")
        } else {
            val notesList = root.get("notes").toList()

            for (i in 0 until finalAmount) {
                val note = notesList[i]
                println(note["title"].asText() + "\n\t" + note["body"].asText() + "\n-=-=-=-=-=-=-=-=-=-=-=-=-=-")
            }
        }
    }
}