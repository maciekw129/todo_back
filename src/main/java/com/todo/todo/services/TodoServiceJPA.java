package com.todo.todo.services;

import com.todo.todo.entities.Todo;
import com.todo.todo.mappers.TodoMapper;
import com.todo.todo.model.TodoDTO;
import com.todo.todo.model.TodoPatchDTO;
import com.todo.todo.repositories.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TodoServiceJPA implements TodoService {

    private final TodoRepository todoRepository;

    private final TodoMapper todoMapper;

    @Override
    public List<TodoDTO> getTodoList(Boolean completed) {

        List<Todo> todoList;

        if(completed != null) {
            todoList = getTodoListByCompleted(completed);
        } else {
            todoList = todoRepository.findAll();
        }

        return todoList
                .stream()
                .map(todoMapper::todoToTodoDto)
                .collect(Collectors.toList());
    }

    public List<Todo> getTodoListByCompleted(boolean completed) {
        return todoRepository.findAllByCompleted(completed);
    }

    @Override
    public TodoDTO saveNewTodo(TodoDTO todo) {
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

    @Override
    public Optional<TodoDTO> updateTodoById(UUID todoId, TodoPatchDTO todoPatchDTO) {
        AtomicReference<Optional<TodoDTO>> atomicReference = new AtomicReference<>();

        todoRepository.findById(todoId).ifPresentOrElse(todoFromDb -> {
            if(StringUtils.hasText(todoPatchDTO.getTodoName())) {
                todoFromDb.setTodoName(todoPatchDTO.getTodoName());
            }

            if(StringUtils.hasText(todoPatchDTO.getTodoDescription())) {
                todoFromDb.setTodoDescription(todoPatchDTO.getTodoDescription());
            }

            if(todoPatchDTO.getCompleted() != null) {
                todoFromDb.setCompleted(todoPatchDTO.getCompleted());
            }

            Todo savedTodo = todoRepository.save(todoFromDb);
            atomicReference.set(Optional.of(todoMapper.todoToTodoDto(savedTodo)));

        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

}
