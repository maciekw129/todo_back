package com.todo.todo.services;

import com.todo.todo.entities.Todo;
import com.todo.todo.mappers.TodoMapper;
import com.todo.todo.model.TodoDTO;
import com.todo.todo.model.TodoPatchDTO;
import com.todo.todo.repositories.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Service
public class TodoServiceJPA implements TodoService {

    private final TodoRepository todoRepository;

    private final TodoMapper todoMapper;

    private static final int DEFAULT_PAGE_SIZE = 25;
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int MAX_PAGE_SIZE = 1000;

    private PageRequest buildPageRequest(Integer pageSize, Integer pageNumber) {
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;

        } else {
            queryPageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize != null && pageSize >= 0) {

            if(pageSize > MAX_PAGE_SIZE) {
                queryPageSize = MAX_PAGE_SIZE;

            } else {
                queryPageSize = pageSize;
            }

        } else {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        return PageRequest.of(queryPageNumber, queryPageSize);
    }

    private Page<Todo> getTodoListByCompleted(boolean completed, Pageable pageable) {
        return todoRepository.findAllByCompleted(completed, pageable);
    }

    @Override
    public Page<TodoDTO> getTodoList(Boolean completed, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageSize, pageNumber);

        Page<Todo> todoPage;

        if(completed != null) {
            todoPage = getTodoListByCompleted(completed, pageRequest);
        } else {
            todoPage = todoRepository.findAll(pageRequest);
        }

        return todoPage.map(todoMapper::todoToTodoDto);
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
