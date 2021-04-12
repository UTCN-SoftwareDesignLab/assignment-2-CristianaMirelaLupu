package com.demo.user.mapper;

import com.demo.user.dto.UserListDTO;
import com.demo.user.dto.UserMinimalDTO;
import com.demo.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "username", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @Mappings({
            @Mapping(target = "username", source = "userListDTO.username"),
            @Mapping(target = "roles", ignore = true)
    })
    User userFromUserListDto (UserListDTO userListDTO);


    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }
}
