package ru.itis.hauntedo.simbirtest.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.itis.hauntedo.simbirtest.dto.request.CreateUserRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateUserRequest;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    User toUserEntity(CreateUserRequest userRequest);

    void toUserEntity(UpdateUserRequest userRequest, @MappingTarget User user);

    List<UserResponse> toList(List<User> users);
}
