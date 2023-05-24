package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemStorageImpl implements ItemStorage {
    private final HashMap<Long, Item> items = new HashMap<>();
    private final AtomicLong generatorId = new AtomicLong();

    @Override
    public List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Item getItem(long id) {
        return items.get(id);
    }

    @Override
    public Item addItem(Item item) {
        item.setId(generatorId.incrementAndGet());
        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    @Override
    public Item updateItem(long id, Item item) {
        return items.put(item.getId(), item);
    }

    @Override
    public boolean deleteItem(long id) {
        return items.remove(id) != null;
    }
}
