package com.todo.todo.Mappers;

import com.todo.todo.entities.Todo;
import com.todo.todo.model.TodoDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TodoMapper {
    Todo todoToTodoDto(Todo todo);

    Todo todoDtoToTodo(TodoDTO todoDTO);
}
