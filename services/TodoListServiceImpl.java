package services;

import entities.TodoList;
import repositories.TodoListRepository;

public class TodoListServiceImpl implements TodoListService{
    private final TodoListRepository todoListRepository;


    public TodoListServiceImpl (final TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }


    @Override
    public TodoList[] getTodoList() {
        return todoListRepository.getAll();
    }

    @Override
    public void addTodoList(final String todo) {
        if (todo.isEmpty() || todo.isBlank()) {
            System.out.println("Masukkan todo yang benar");
            return;
        }

        TodoList todoList = new TodoList();
        todoList.setTodo(todo);
        todoListRepository.add(todoList);
    }

    @Override
    public Boolean removeTodoList(final Integer number) {
        return removeTodoList(number);
    }

    @Override
    public Boolean editTodoList(Integer number, String todo) {
        TodoList todoList = new TodoList();
        todoList.setId(number);
        todoList.setTodo(todo);
        return todoListRepository.edit(todoList);
    }
}
