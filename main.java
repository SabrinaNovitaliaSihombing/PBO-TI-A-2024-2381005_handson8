import entities.TodoList;
import repositories.TodoListRepositoryDblmpl;
import repositories.TodoListRepositoryImpl;
import repositories.TodoListRepository;
import services.TodoListService;
import services.TodoListServiceImpl;
import views.TodoListTerminalView;
import views.TodoListView;

import javax.xml.crypto.Data;

import config.Database;

public class main {
    public static void main(String[] args) {
        Database database = new Database("my_database", "root", "", "localhost", "3306");
        database.setup();

        TodoListRepository todoListRepository = new TodoListRepositoryDblmpl(database);
        TodoListService todoListService = new TodoListServiceImpl(todoListRepository);
        TodoListView todoListView = new TodoListTerminalView(todoListService);
        todoListView.run();

    }
}