package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return userClient.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        return userClient.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Validated(UserDto.NewUser.class) @RequestBody UserDto user) {
        return userClient.addUser(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@Validated(UserDto.UpdateUser.class) @RequestBody UserDto user, @PathVariable long id) {
        return userClient.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        return userClient.deleteUser(id);
    }
}
