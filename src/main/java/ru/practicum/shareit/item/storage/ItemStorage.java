package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    List<Item> getAllItems();

    Item getItem(int id);

    Item addItem(Item item);

    Item updateItem(int id, Item item);

    boolean deleteItem(int id);
}
