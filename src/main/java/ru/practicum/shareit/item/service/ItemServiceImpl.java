package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public List<Item> getOwnerItems(int ownerId) {
        return itemStorage.getAllItems()
                .stream()
                .filter(x -> x.getOwner()
                        .getId() == ownerId)
                .collect(Collectors.toList());
    }

    @Override
    public Item getItem(int id) {
        return itemStorage.getItem(id);
    }

    @Override
    public Item addItem(Item item, int ownerId) {
        User owner = userStorage.get(ownerId);
        if (owner != null) {
            item.setOwner(owner);
            return itemStorage.addItem(item);
        }
        throw new NotFoundException("No such owner");
    }

    @Override
    public Item updateItem(Item item, int ownerId, int id) {
        Item currentItem = itemStorage.getItem(id);
        if (currentItem.getOwner().getId() == ownerId) {
            if (item.getName() != null) currentItem.setName(item.getName());
            if (item.getDescription() != null) currentItem.setDescription(item.getDescription());
            if (item.getAvailable() != null) currentItem.setAvailable(item.getAvailable());
            return itemStorage.updateItem(id, currentItem);
        }
        throw new NotFoundException("Wrong owner id");
    }

    @Override
    public boolean deleteItem(int id) {
        return itemStorage.deleteItem(id);
    }

    @Override
    public List<Item> getItemByNameOrDescription(String text) {
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        return itemStorage.getAllItems()
                .stream()
                .filter(Item::getAvailable)
                .filter(x -> x.getName().toLowerCase().contains(text)
                        || x.getDescription().toLowerCase().contains(text))
                .collect(Collectors.toList());
    }
}
