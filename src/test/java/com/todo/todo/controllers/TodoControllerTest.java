package com.todo.todo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.todo.model.TodoDTO;
import com.todo.todo.services.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@WebMvcTest(TodoController.class)
class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<TodoDTO> todoDTOArgumentCaptor;

    @Test
    void get_todo_list_should_return_list_of_todos() throws Exception {
        TodoDTO todo1 = TodoDTO.builder()
                .todoName("test1")
                .todoDescription("test1")
                .build();

        TodoDTO todo2 = TodoDTO.builder()
                .todoName("test2")
                .todoDescription("test2")
                .build();

        List<TodoDTO> todoList = Arrays.asList(todo1, todo2);

        given(todoService.getTodoList()).willReturn(todoList);

        mockMvc.perform(get(TodoController.TODO_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void post_todo_should_create_and_return_new_todo() throws Exception {
        TodoDTO newTodo = TodoDTO.builder()
                .todoName("new todo")
                .todoDescription("new todo description")
                .build();

        given(todoService.saveNewTodo(newTodo)).willReturn(newTodo);

        mockMvc.perform(post(TodoController.TODO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        verify(todoService).saveNewTodo(todoDTOArgumentCaptor.capture());
        assertThat(todoDTOArgumentCaptor.getValue().getTodoName()).isEqualTo(newTodo.getTodoName());
    }

    @Test
    void post_todo_should_throw_exception_when_blank_name_and_description() throws Exception {
        TodoDTO newTodo = TodoDTO.builder()
                .todoName("")
                .todoDescription("")
                .build();

        mockMvc.perform(post(TodoController.TODO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()", is(4)));
    }

    @Test
    void post_todo_should_throw_exception_when_null_name_and_description() throws Exception {
        TodoDTO newTodo = TodoDTO.builder().build();

        mockMvc.perform(post(TodoController.TODO_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()", is(4)));
    }

    @Test
    void post_todo_should_throw_exception_when_name_and_description_too_short() throws Exception {
        TodoDTO newTodo = TodoDTO.builder()
                .todoName("1")
                .todoDescription("1")
                .build();

        mockMvc.perform(post(TodoController.TODO_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()", is(2)));
    }

    @Test
    void post_todo_should_throw_exception_when_name_and_description_too_long() throws Exception {
        TodoDTO newTodo = TodoDTO.builder()
                .todoName("123456789012345678901234567890123456789012345678901234567890")
                .todoDescription("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890")
                .build();

        mockMvc.perform(post(TodoController.TODO_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()", is(2)));
    }

    @Test
    void delete_todo_by_id_should_delete_todo() throws Exception {
        TodoDTO todoToDelete = TodoDTO.builder()
                .id(UUID.randomUUID())
                .todoName("todo to delete")
                .todoDescription("todo description")
                .build();

        given(todoService.deleteTodoById(todoToDelete.getId())).willReturn(true);

        mockMvc.perform(delete(TodoController.TODO_PATH_ID, todoToDelete.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(todoService).deleteTodoById(uuidArgumentCaptor.capture());
        assertThat(todoToDelete.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void delete_todo_by_id_should_throw_exception_when_not_found() throws Exception {
        TodoDTO todoToDelete = TodoDTO.builder()
                .id(UUID.randomUUID())
                .todoName("todo to delete")
                .todoDescription("todo description")
                .build();

        given(todoService.deleteTodoById(todoToDelete.getId())).willReturn(false);

        mockMvc.perform(delete(TodoController.TODO_PATH_ID, todoToDelete.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(todoService).deleteTodoById(uuidArgumentCaptor.capture());
        assertThat(todoToDelete.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }
}