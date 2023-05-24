package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserStorageImpl implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private final AtomicLong generatorId = new AtomicLong();

    @Override
    public User add(User user) {
        user.setId(generatorId.incrementAndGet());
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User update(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean delete(long id) {
        return users.remove(id) != null;
    }

    @Override
    public User get(long id) {
        return users.get(id);
    }
}
