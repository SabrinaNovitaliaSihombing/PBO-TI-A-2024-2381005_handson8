package entities;

public class TodoList {
    public int getId() {
        return id;
    }

    public TodoList() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    private String todo;
    private int id;

}
