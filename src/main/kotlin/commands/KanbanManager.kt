package commands
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.File

class KanbanManager() {
    private var mapper = ObjectMapper()
    private val file = File("src/main/data/kanban.json");
    private var root: ObjectNode = this.mapper.readValue(file, ObjectNode::class.java)

    fun create(title: String) {
        println("What is the description of $title?")
        var desc = readln()
        if (desc == "") {
            desc = "Placeholder description"
        }
        val projectsArray = root.get("projects") as ObjectNode
        val newKanban = mapper.createObjectNode()
        newKanban.put("title", title)
        newKanban.put("description", desc)
        val progress = mapper.createObjectNode()
        progress.put("backlog", mapper.createArrayNode())
        progress.put("todo", mapper.createArrayNode())
        progress.put("progress", mapper.createArrayNode())
        progress.put("testing", mapper.createArrayNode())
        progress.put("done", mapper.createArrayNode())
        newKanban.put("kanban", progress)
        projectsArray.put(title, newKanban)
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, root)
    }

    fun view(title: String){
        val projectsArray = root.get("projects") as ObjectNode
        val project = projectsArray.get(title)
        val kanbanData = project.get("kanban")
        val backlog = kanbanData.get("backlog") as ArrayNode
        val todo = kanbanData.get("todo") as ArrayNode
        val progress = kanbanData.get("progress") as ArrayNode
        val testing = kanbanData.get("testing") as ArrayNode
        val done = kanbanData.get("done") as ArrayNode
        println(project.get("title").asText())
        println("\n" + project.get("description").asText())
        println("-------------------------------\nBacklog: " + backlog.size())
        for (i in 0 until backlog.size()) {
            val entry = backlog[i]
            println("• " + entry.asText());
        }
        println("-------------------------------\nTodo: " + todo.size())
        for (i in 0 until todo.size()) {
            val entry = todo[i]
            println("• " + entry.asText());
        }
        println("-------------------------------\nProgress: " + progress.size())
        for (i in 0 until progress.size()) {
            val entry = progress[i]
            println("• " + entry.asText());
        }
        println("-------------------------------\nTesting: " + testing.size())
        for (i in 0 until testing.size()) {
            val entry = testing[i]
            println("• " + entry.asText());
        }
        println("-------------------------------\nDone: " + done.size())
        for (i in 0 until done.size()) {
            val entry = done[i]
            println("• " + entry.asText());
        }
        println("-------------------------------")
    }

    fun update(title: String){
        val projectsArray = root.get("projects") as ObjectNode
        val project = projectsArray.get(title)
        val kanbanData = project.get("kanban")
        println("What category do you want to edit?\n(backlog, todo, progress, testing, done)")
        val categoryAns = readln()
        var data = kanbanData.get(categoryAns) as ArrayNode
        for (i in 0 until data.size()) {
            val entry = data[i]
            println("$i: ${entry.asText()}")
        }
        println("What task do you want to edit?")
        val taskAns = readln()
        println("Where do you want to move it?\n(backlog, todo, progress, testing, done)")
        val destinationAns = readln()
        val temp = kanbanData.get(categoryAns).get(taskAns.toInt())
        (kanbanData.get(categoryAns) as ArrayNode).remove(taskAns.toInt())
        val destination = kanbanData.get(destinationAns) as ArrayNode
        destination.add(temp)
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, root)
        view(title)
    }
}
