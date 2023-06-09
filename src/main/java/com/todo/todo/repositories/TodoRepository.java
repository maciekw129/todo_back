package com.todo.todo.repositories;

import com.todo.todo.entities.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
    Page<Todo> findAllByCompleted(boolean completed, Pageable pageable);
}
