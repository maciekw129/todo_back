package com.todo.todo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class TodoDTO {
    private UUID id;

    private Integer version;

    @Size(min = 3, max = 50, message = "Invalid name: name must be of 3 to 50 characters.")
    @NotNull(message = "Invalid name: name can't be NULL.")
    @NotBlank(message = "Invalid name: name can't be empty.")
    private String todoName;

    @Size(min = 3, max = 255, message = "Invalid description: description must be of 3 to 255 characters.")
    @NotNull(message = "Invalid description: description can't be NULL.")
    @NotBlank(message = "Invalid description: description can't be empty.")
    private String todoDescription;

    @NotNull
    private boolean completed;

    private LocalDateTime createdDate;
}
