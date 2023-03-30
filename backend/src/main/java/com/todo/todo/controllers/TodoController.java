package com.todo.todo.controllers;

import com.todo.todo.model.TodoDTO;
import com.todo.todo.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TodoController {

    private static final String TODO_PATH = "/api/v1/todo";

    private final TodoService todoService;

    @GetMapping(TODO_PATH)
    public List<TodoDTO> getTodoList() {
        return todoService.getTodoList();
    }

    @PostMapping(TODO_PATH)
    public ResponseEntity postTodo(@RequestBody TodoDTO todo) {
        TodoDTO newTodo = todoService.saveNewTodo(todo);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/todo/" + newTodo.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
}
