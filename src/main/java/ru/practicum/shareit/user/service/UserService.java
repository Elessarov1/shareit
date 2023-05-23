package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(int id);

    User addUser(User user);

    User updateUser(int id, User user);

    boolean deleteUser(int id);
}
