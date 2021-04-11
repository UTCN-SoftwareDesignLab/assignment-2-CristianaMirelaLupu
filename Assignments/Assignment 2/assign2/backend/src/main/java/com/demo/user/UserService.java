package com.demo.user;

import com.demo.user.dto.UserListDTO;
import com.demo.user.dto.UserMinimalDTO;
import com.demo.user.mapper.UserMapper;
import com.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

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
        userRepository.save(userToSave);

        return user;
    }

    public UserListDTO edit(UserListDTO user) {

        User actUser = findById(user.getId());
        actUser.setUsername(user.getName());
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
