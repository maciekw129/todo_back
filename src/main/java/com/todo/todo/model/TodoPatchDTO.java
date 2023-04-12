package com.todo.todo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class TodoPatchDTO {
    @Size(min = 3, max = 50, message = "Invalid name: name must be of 3 to 50 characters.")
    private String todoName;

    @Size(min = 3, max = 255, message = "Invalid description: description must be of 3 to 255 characters.")
    private String todoDescription;

    private Boolean completed;
}
