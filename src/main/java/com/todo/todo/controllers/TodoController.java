package com.todo.todo.controllers;

import com.todo.todo.exceptions.NotFoundException;
import com.todo.todo.model.TodoDTO;
import com.todo.todo.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoController {

    private static final String TODO_PATH = "/api/v1/todo";
    private static final String TODO_PATH_ID = TODO_PATH + "/{todoId}";

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

        return new ResponseEntity<TodoDTO>(newTodo, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(TODO_PATH_ID)
    public ResponseEntity deleteTodoById(@PathVariable UUID todoId) {
        if(!todoService.deleteTodoById(todoId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
