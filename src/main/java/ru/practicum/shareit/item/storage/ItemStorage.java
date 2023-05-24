package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    List<Item> getAllItems();

    Item getItem(long id);

    Item addItem(Item item);

    Item updateItem(long id, Item item);

    boolean deleteItem(long id);
}
