package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.mapper.Mappers;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return Mappers.userToDto(userService.getUserById(id));
    }

    @PostMapping
    public UserDto addUser(@Validated(UserDto.NewUser.class) @RequestBody UserDto user) {
        return Mappers.userToDto(userService.addUser(Mappers.dtoToUser(user)));
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@Validated(UserDto.UpdateUser.class) @RequestBody UserDto user, @PathVariable long id) {
        return Mappers.userToDto(userService.updateUser(id, Mappers.dtoToUser(user)));
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }
}
