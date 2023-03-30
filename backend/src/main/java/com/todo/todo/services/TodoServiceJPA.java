package com.todo.todo.services;

import com.todo.todo.entities.Todo;
import com.todo.todo.mappers.TodoMapper;
import com.todo.todo.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TodoServiceJPA implements TodoService {

    private final TodoRepository todoRepository;

    private final TodoMapper todoMapper;

    @Override
    public List<Todo> getTodoList() {
        return todoRepository.findAll()
                .stream()
                .map(todoMapper::todoToTodoDto)
                .collect(Collectors.toList());
    }
}
