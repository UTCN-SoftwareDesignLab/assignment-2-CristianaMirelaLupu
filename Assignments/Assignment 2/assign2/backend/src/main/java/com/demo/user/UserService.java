package com.demo.user;

import com.demo.user.dto.UserListDTO;
import com.demo.user.dto.UserMinimalDTO;
import com.demo.user.mapper.UserMapper;
import com.demo.user.model.ERole;
import com.demo.user.model.Role;
import com.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public UserListDTO create(UserListDTO user) {
        User userToSave = userMapper.userFromUserListDto(user);
        userToSave.setPassword(encoder.encode(user.getPassword()));

        Set<String> rolesStr = user.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }
        userToSave.setRoles(roles);//.stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
        userRepository.save(userToSave);

        return user;
    }

    public UserListDTO edit(UserListDTO user) {

        User actUser = findById(user.getId());
        actUser.setUsername(user.getUsername());
        actUser.setEmail(user.getEmail());

        return userMapper.userListDtoFromUser(
                userRepository.save(actUser)
        );
    }

    public void deleteAll (){
        userRepository.deleteAll();
    }

    public void deleteById (Long id) {
        userRepository.deleteById(id);
    }

}
