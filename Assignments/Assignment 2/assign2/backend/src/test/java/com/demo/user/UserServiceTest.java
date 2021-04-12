package com.demo.user;

import com.demo.TestCreationFactory;
import com.demo.user.dto.UserListDTO;
import com.demo.user.mapper.UserMapper;
import com.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, encoder, roleRepository);
        userService.deleteAll();
    }


    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        List<UserListDTO> all = userService.allUsersForList();

        Assertions.assertEquals(users.size(), all.size());
    }

    @Test
    void findById(){

        List <User> reqUser = TestCreationFactory.listOf(User.class);
        when(userRepository.save(reqUser.get(0))).thenReturn(reqUser.get(0));

        UserListDTO user = userMapper.userListDtoFromUser(reqUser.get(0));
        userService.create(user);

        Assertions.assertNotNull(userService.findById(user.getId()));
    }

    @Test
    void create() {

        List <User> reqUser = TestCreationFactory.listOf(User.class);
        when(userRepository.save(reqUser.get(0))).thenReturn(reqUser.get(0));

        UserListDTO user = userMapper.userListDtoFromUser(reqUser.get(0));
        userService.create(user);

        Assertions.assertNotNull(userService.findById(user.getId()));
    }

    @Test
    void update() {

        List <User> reqUser = TestCreationFactory.listOf(User.class);
        when(userRepository.save(reqUser.get(0))).thenReturn(reqUser.get(0));

        UserListDTO user = userMapper.userListDtoFromUser(reqUser.get(0));
        userService.edit(user);
        userService.create(user);

        Assertions.assertNotNull(userService.findById(user.getId()));
    }

    @Test
    void deleteAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        userRepository.deleteAll();

        List<UserListDTO> all = userService.allUsersForList();

        Assertions.assertEquals(0, all.size());
    }

    @Test
    void deleteById() {

        List <User> reqUser = TestCreationFactory.listOf(User.class);
        when(userRepository.save(reqUser.get(0))).thenReturn(reqUser.get(0));

        UserListDTO user = userMapper.userListDtoFromUser(reqUser.get(0));
        userService.deleteById(user.getId());

        Assertions.assertNull(userService.findById(user.getId()));
    }
}