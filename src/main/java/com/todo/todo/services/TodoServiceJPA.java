package com.todo.todo.services;

import com.todo.todo.mappers.TodoMapper;
import com.todo.todo.model.TodoDTO;
import com.todo.todo.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoServiceJPA implements TodoService {

    private final TodoRepository todoRepository;

    private final TodoMapper todoMapper;

    @Override
    public List<TodoDTO> getTodoList() {
        return todoRepository.findAll()
                .stream()
                .map(todoMapper::todoToTodoDto)
                .collect(Collectors.toList());
    }

    @Override
    public TodoDTO saveNewTodo(TodoDTO todo) {
        todo.setCreatedDate(LocalDateTime.now());
        return todoMapper.todoToTodoDto(todoRepository.save(todoMapper.todoDtoToTodo(todo)));
    }

    @Override
    public Boolean deleteTodoById(UUID todoId) {
        if(todoRepository.existsById(todoId)) {
            todoRepository.deleteById(todoId);
            return true;
        }

        return false;
    }

}
