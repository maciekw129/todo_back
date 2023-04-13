package com.todo.todo.services;

import com.todo.todo.entities.Todo;
import com.todo.todo.mappers.TodoMapper;
import com.todo.todo.model.TodoDTO;
import com.todo.todo.repositories.TodoRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TodoServiceJPATest {
    @Mock
    TodoRepository todoRepository;

    @Mock
    TodoMapper todoMapper;

    @InjectMocks
    TodoServiceJPA todoService;

    @Test
    void get_todo_user_should_return_users_list() {
        Todo todo1 = Todo.builder()
                .todoName("test1")
                .todoDescription("test1")
                .build();

        TodoDTO todoDTO1 = TodoDTO.builder()
                .todoName("test1")
                .todoDescription("test1")
                .build();

        Todo todo2 = Todo.builder()
                .todoName("test2")
                .todoDescription("test2")
                .build();

        TodoDTO todoDTO2 = TodoDTO.builder()
                .todoName("test2")
                .todoDescription("test2")
                .build();

        List<Todo> todoList = Arrays.asList(todo1, todo2);

        given(todoMapper.todoToTodoDto(todo1)).willReturn(todoDTO1);
        given(todoMapper.todoToTodoDto(todo2)).willReturn(todoDTO2);
        given(todoRepository.findAll()).willReturn(todoList);

        List<TodoDTO> listReturned = todoService.getTodoList(null);

        assertThat(listReturned.get(0).getTodoName()).isEqualTo(todo1.getTodoName());
        assertThat(listReturned.get(0).getTodoDescription()).isEqualTo(todo1.getTodoDescription());

        assertThat(listReturned.get(1).getTodoName()).isEqualTo(todo2.getTodoName());
        assertThat(listReturned.get(1).getTodoDescription()).isEqualTo(todo2.getTodoDescription());
    }

    @Test
    void save_new_todo_should_return_new_todo() {
        Todo todo = Todo.builder()
                .todoName("new todo")
                .todoDescription("new todo description")
                .build();

        TodoDTO todoDTO = TodoDTO.builder()
                .todoName("new todo")
                .todoDescription("new todo description")
                .build();

        given(todoMapper.todoToTodoDto(todo)).willReturn(todoDTO);
        given(todoMapper.todoDtoToTodo(todoDTO)).willReturn(todo);
        given(todoRepository.save(todo)).willReturn(todo);

        TodoDTO result = todoService.saveNewTodo(todoDTO);

        assertThat(result).isEqualTo(todoDTO);
    }

    @Test
    void delete_todo_by_id_should_return_true() {
        UUID todoId = UUID.randomUUID();

        given(todoRepository.existsById(any(UUID.class))).willReturn(true);

        Boolean result = todoService.deleteTodoById(todoId);

        assertThat(result).isTrue();
    }

    @Test
    void delete_todo_by_id_should_return_false() {
        UUID todoId = UUID.randomUUID();

        given(todoRepository.existsById(any(UUID.class))).willReturn(false);

        Boolean result = todoService.deleteTodoById(todoId);

        assertThat(result).isFalse();
    }
}