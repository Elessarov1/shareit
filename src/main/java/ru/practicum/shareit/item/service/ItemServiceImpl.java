package ru.practicum.shareit.item.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.mapper.Mappers;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;
    UserRepository userRepository;
    BookingRepository bookingRepository;

    @Override
    public List<ItemResponseDto> getOwnerItems(long ownerId) {
        List<Item> itemList = itemRepository.findAllByOwnerIdOrderById(ownerId);
        List<Long> itemIds = itemList.stream()
                .map(Item::getId)
                .collect(Collectors.toList());
        List<Booking> bookingList = bookingRepository.findAllByOwnerIdAndItemIds(ownerId, itemIds);
        return itemList.stream()
                .map(item -> Mappers.itemToResponseDto(item, bookingList))
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponseDto getItem(long id, long ownerId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such item"));
        List<Booking> bookingList = bookingRepository.findAllByOwnerIdAndItemId(ownerId, id);
        return Mappers.itemToResponseDto(item, bookingList);
    }

    @Override
    public Item addItem(Item item, long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("No such owner"));
        item.setOwner(owner);
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Item item, long ownerId, long id) {
        Item currentItem = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such item"));
        if (currentItem.getOwner().getId() != ownerId) {
            throw new NotFoundException("Wrong owner id");
        }
        if (item.getName() != null) currentItem.setName(item.getName());
        if (item.getDescription() != null) currentItem.setDescription(item.getDescription());
        if (item.getAvailable() != null) currentItem.setAvailable(item.getAvailable());
        currentItem.setId(id);
        return itemRepository.save(currentItem);
    }

    @Override
    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> getItemByNameOrDescription(String text) {
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        return itemRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue(text, text);
    }
}
