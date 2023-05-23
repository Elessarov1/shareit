package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userStorage.get(id);
    }

    @Override
    public User addUser(User user) {
        if (isDuplicateEmail(user)) {
            throw new ValidationException("Email already in used");
        }
        return userStorage.add(user);
    }

    @Override
    public User updateUser(int id, User user) {
        user.setId(id);
        String name = user.getName();
        String email = user.getEmail();
        User userFromStorage = userStorage.get(id);
        if (name != null) {
            userFromStorage.setName(name);
        }
        if (email != null) {
            if (isDuplicateEmail(user)) {
                throw new ValidationException("Email already in used");
            }
            userFromStorage.setEmail(email);
        }
        return userStorage.update(userFromStorage);
    }

    @Override
    public boolean deleteUser(int id) {
        return userStorage.delete(id);
    }

    private boolean isDuplicateEmail(User user) {
        return userStorage.getAllUsers().stream()
                .filter(x -> x.getId() != user.getId())
                .anyMatch(user1 -> user1.getEmail().equals(user.getEmail()));
    }
}
