package com.todo.todo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Todo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @Column(length = 50)
    @Size(min = 3, max = 50)
    @NotNull
    @NotBlank
    private String todoName;

    @Column(length = 255)
    @Size(min = 3, max=255)
    @NotNull
    @NotBlank
    private String todoDescription;

    @NotNull
    private boolean completed = false;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
