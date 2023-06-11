package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<ItemResponseDto> getOwnerItems(long ownerId);

    ItemResponseDto getItem(long id, long ownerId);

    Item addItem(Item item, long ownerId);

    Item updateItem(Item item, long ownerId, long id);

    void deleteItem(long id);

    List<Item> getItemByNameOrDescription(String text);
}
