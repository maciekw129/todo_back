package com.todo.todo.services;

import com.todo.todo.model.TodoDTO;
import com.todo.todo.model.TodoPatchDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoService {

    List<TodoDTO> getTodoList(Boolean completed);

    TodoDTO saveNewTodo(TodoDTO todo);

    Boolean deleteTodoById(UUID todoId);

    Optional<TodoDTO> updateTodoById(UUID todoId, TodoPatchDTO todoPatchDTO);
}
