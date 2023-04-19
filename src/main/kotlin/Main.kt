import commands.*
fun main(args: Array<String>) {
    if(args.isEmpty()){
        println("Welcome to Personal Organization System or POS for short")
        println("----------------------Description-----------------------\n" +
                "A lightweight command line productivity organizer. Built to allow people who are more comfortable with" +
                "the command line to organize themselves")
        println("-----------------------Functions------------------------")
        println("config\n  Description: Opens config menu to edit parameters of the program")
        println("note [mode]\n  mode:\n    read - [amount]\n    write - [title] [body]")
        println("kanban [mode]\n  mode:\n    create - [title]\n    view - [title]")
    }else if (args[0] == "note"){
        val noteManager = NoteManager()
        if(args[1] == "write") {
            noteManager.write(args[2], args[3])
        }else if (args[1] == "read"){
            if(args.size == 2){
                println(noteManager.read(-1))
            }else {
                println(noteManager.read(Integer.parseInt(args[2])))
            }
        }
    }else if(args[0] == "kanban"){
        val kanbanManager = KanbanManager()
        if(args[1] == "create"){
            kanbanManager.create(args[2])
        }else if(args[1] == "view"){
            kanbanManager.view(args[2])
        }else if(args[1] == "update"){
            kanbanManager.update(args[2])
        }
    }else if(args[0] == "news") {
        val newsManager = NewsManager()
        newsManager.default()
    }else {
            println("That is not a recognizable command")
    }
}