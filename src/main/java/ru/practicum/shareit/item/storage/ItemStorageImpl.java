package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class ItemStorageImpl implements ItemStorage {
    private final HashMap<Integer, Item> items = new HashMap<>();
    private int generatorId = 1;

    @Override
    public List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Item getItem(int id) {
        return items.get(id);
    }

    @Override
    public Item addItem(Item item) {
        item.setId(generatorId++);
        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    @Override
    public Item updateItem(int id, Item item) {
        return items.put(item.getId(), item);
    }

    @Override
    public boolean deleteItem(int id) {
        return items.remove(id) != null;
    }
}
