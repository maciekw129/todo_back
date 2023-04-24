package com.todo.todo.annotations.uniqueEmail;

import com.todo.todo.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid (String email, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(email);
    }
}
