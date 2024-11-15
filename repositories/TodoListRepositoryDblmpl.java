package repositories;

import config.Database;
import entities.TodoList;

import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TodoListRepositoryDblmpl implements TodoListRepository {
    private final Database database;

    public  TodoListRepositoryDblmpl(final Database database) {
        this.database = database;
    }

    @Override
    public TodoList[] getALL() {
        Connection connection = database.getConnection();
        String sqlStatement = "SELECT * FROM todos";
        List<TodoList> todoLists = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TodoList todoList = new TodoList();
                Integer id = resultSet.getInt(1);
                String todo = resultSet.getString(2);
                todoList.setTodo(todo);
                todoList.setId(id);
                todoLists.add(todoList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return  todoLists.toArray(TodoList[]::new);
    }

    @Override
    public TodoList[] getAll() {
        return new TodoList[0];
    }

    @Override
    public void add(final TodoList todoList) {
        Connection connection = database.getConnection();
        String sqlStatement = "INSERT INTO todos(todo) VALUES(?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, todoList.getTodo());
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Insert seccesfull !");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Integer getDbId(final Integer id) {
        TodoList[] todoLists = getALL();
        Long countElement = Arrays.stream(todoLists).filter(Objects::nonNull).count();
        if (countElement.intValue() == 0) {
            return null;
        }
        var dbId = todoLists[id - 1].getId();
        return dbId;
    }

    @Override
    public Boolean remove(final Integer id) {
        String sqlStatement = "DELETE FROM todos WHERE id = ?";
        Connection connection = database.getConnection();
        var dbId = getDbId(id);
        if (dbId == null) {
            return false;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, dbId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Delete Seccessful !");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean edit(final TodoList todoList) {
        String sqlStatement = "UPDATE todos set todo = ? WHERE id = ?";
        Connection connection = database.getConnection();
        var dbId = getDbId(todoList.getId());
        if (dbId == null) {
            return false;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, todoList.getTodo());
            preparedStatement.setInt(2, dbId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful !");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}