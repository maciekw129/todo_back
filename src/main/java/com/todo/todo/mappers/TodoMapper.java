package com.todo.todo.mappers;

import com.todo.todo.entities.Todo;
import com.todo.todo.model.TodoDTO;
import com.todo.todo.model.TodoPatchDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TodoMapper {
    TodoDTO todoToTodoDto(Todo todo);

    Todo todoDtoToTodo(TodoDTO todoDTO);

    TodoDTO todoPatchDtoToTodoDto(TodoPatchDTO todoPatchDTO);
}
