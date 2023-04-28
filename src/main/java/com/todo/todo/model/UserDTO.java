package com.todo.todo.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class UserDTO {
    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
}
