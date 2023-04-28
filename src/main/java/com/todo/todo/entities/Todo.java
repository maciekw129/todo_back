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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo")
@Entity
public class Todo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
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

    @ManyToOne
    private User user;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
