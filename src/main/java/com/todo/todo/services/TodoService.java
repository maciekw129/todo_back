package com.todo.todo.services;

import com.todo.todo.model.TodoDTO;
import com.todo.todo.model.TodoPatchDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface TodoService {

    Page<TodoDTO> getTodoList(Boolean completed, Integer pageNumber, Integer pageSize);

    TodoDTO saveNewTodo(TodoDTO todo);

    Boolean deleteTodoById(UUID todoId);

    Optional<TodoDTO> updateTodoById(UUID todoId, TodoPatchDTO todoPatchDTO);
}
