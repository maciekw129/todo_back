package com.todo.todo.mappers;

import com.todo.todo.entities.Todo;
import com.todo.todo.model.TodoDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TodoMapper {
    TodoDTO todoToTodoDto(Todo todo);

    Todo todoDtoToTodo(TodoDTO todoDTO);
}
