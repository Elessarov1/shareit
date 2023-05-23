package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {
    User add(User user);

    User update(User user);

    List<User> getAllUsers();

    boolean delete(int id);

    User get(int id);
}
