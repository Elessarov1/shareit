package ru.practicum.shareit.item.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.mapper.Mappers;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
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
    CommentRepository commentRepository;

    @Override
    public List<ItemResponseDto> getOwnerItems(long ownerId) {
        List<Item> itemList = itemRepository.findAllByOwnerIdOrderById(ownerId);
        List<Long> itemIds = itemList.stream()
                .map(Item::getId)
                .collect(Collectors.toList());
        List<Booking> bookingList = bookingRepository.findAllByOwnerIdAndItemIds(ownerId, itemIds);
        List<CommentDto> comments = commentRepository.findAllByItemIds(itemIds).stream()
                .map(Mappers::commentToDto)
                .collect(Collectors.toList());
        return itemList.stream()
                .map(item -> Mappers.itemToResponseDto(item, bookingList, comments))
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponseDto getItem(long id, long ownerId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such item"));
        List<Booking> bookingList = bookingRepository.findAllByOwnerIdAndItemId(ownerId, id);
        List<CommentDto> comments = commentRepository.findAllByItemId(id).stream()
                .map(Mappers::commentToDto)
                .collect(Collectors.toList());
        return Mappers.itemToResponseDto(item, bookingList, comments);
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

    @Transactional
    @Override
    public Comment addComment(long ownerId, long itemId, Comment comment) {
        if (bookingRepository.findAllByBookerIdAndItemIdAndEndBefore(ownerId, itemId, LocalDateTime.now()).isEmpty())
            throw new BadRequestException("user doesn't have bookings for this item");

        User user = userRepository.findById(ownerId).orElseThrow(() -> new NotFoundException("no such user"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("no such item"));
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
