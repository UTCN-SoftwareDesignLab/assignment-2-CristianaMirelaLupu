package com.demo.user;

import com.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.demo.UrlMapping.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public UserListDTO create(@RequestBody UserListDTO user){return userService.create(user);}

    @PatchMapping
    public UserListDTO edit (@RequestBody UserListDTO user){return userService.edit(user);}

    @DeleteMapping
    public void deleteAll() { userService.deleteAll(); }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable String id) { userService.deleteById(Long.parseLong(id));}
}
