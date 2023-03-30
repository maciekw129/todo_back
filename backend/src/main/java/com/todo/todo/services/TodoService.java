package com.todo.todo.services;

import com.todo.todo.model.TodoDTO;

import java.util.List;

public interface TodoService {

    List<TodoDTO> getTodoList();

    TodoDTO saveNewTodo(TodoDTO todo);
}
