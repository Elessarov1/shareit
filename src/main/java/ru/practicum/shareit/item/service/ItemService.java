package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getOwnerItems(long ownerId);

    Item getItem(long id);

    Item addItem(Item item, long ownerId);

    Item updateItem(Item item, long ownerId, long id);

    boolean deleteItem(long id);

    List<Item> getItemByNameOrDescription(String text);
}
