package com.todo.todo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class TodoDTO {
    private UUID id;

    private Integer version;

    private String todoName;

    private String todoDescription;

    private LocalDateTime createdDate;
}
