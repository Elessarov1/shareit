package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getOwnerItems(int ownerId);

    Item getItem(int id);

    Item addItem(Item item, int ownerId);

    Item updateItem(Item item, int ownerId, int id);

    boolean deleteItem(int id);

    List<Item> getItemByNameOrDescription(String text);
}
