package com.todo.todo.controllers;

import com.todo.todo.exceptions.NotFoundException;
import com.todo.todo.model.TodoDTO;
import com.todo.todo.model.TodoPatchDTO;
import com.todo.todo.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoController {

    public static final String TODO_PATH = "/api/v1/todo";
    public static final String TODO_PATH_ID = TODO_PATH + "/{todoId}";

    private final TodoService todoService;

    @GetMapping(TODO_PATH)
    public Page<TodoDTO> getTodoList(@RequestParam(required = false) Boolean completed,
                                     @RequestParam(required = false) Integer pageNumber,
                                     @RequestParam(required = false) Integer pageSize) {
        return todoService.getTodoList(completed, pageNumber, pageSize);
    }

    @PostMapping(TODO_PATH)
    public ResponseEntity<TodoDTO> postTodo(@RequestBody @Validated TodoDTO todo) {
        TodoDTO newTodo = todoService.saveNewTodo(todo);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/todo/" + newTodo.getId());

        return new ResponseEntity<TodoDTO>(newTodo, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(TODO_PATH_ID)
    public ResponseEntity deleteTodoById(@PathVariable("todoId") UUID todoId) {
        if(!todoService.deleteTodoById(todoId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(TODO_PATH_ID)
    public ResponseEntity<TodoDTO> patchTodo(@PathVariable("todoId") UUID todoId, @Validated @RequestBody TodoPatchDTO todo) {
        Optional<TodoDTO> todoDTO = todoService.updateTodoById(todoId, todo);

        if(todoDTO.isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<TodoDTO>(todoDTO.get(), HttpStatus.OK);
    }
}
