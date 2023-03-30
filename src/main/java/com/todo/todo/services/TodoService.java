package com.todo.todo.services;

import com.todo.todo.model.TodoDTO;

import java.util.List;
import java.util.UUID;

public interface TodoService {

    List<TodoDTO> getTodoList();

    TodoDTO saveNewTodo(TodoDTO todo);

    Boolean deleteTodoById(UUID todoId);
}
