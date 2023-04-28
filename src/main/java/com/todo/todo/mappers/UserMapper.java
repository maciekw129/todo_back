package com.todo.todo.mappers;

import com.todo.todo.entities.User;
import com.todo.todo.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper()
public interface UserMapper {
    User userDtoToUser(UserDTO userDto);
    UserDTO userToUserDto(User user);
}
