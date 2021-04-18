package com.demo.user;

import com.demo.TestCreationFactory;
import com.demo.book.model.Book;
import com.demo.book.model.dto.BookDTO;
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
    void deleteAll() {
        userRepository.deleteAll();
        List<UserListDTO> all = userService.allUsersForList();

        Assertions.assertEquals(0, all.size());
    }
}