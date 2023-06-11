package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.mapper.Mappers;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemController {
    ItemService itemService;

    @GetMapping
    public List<ItemResponseDto> getOwnerItems(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.getOwnerItems(ownerId);
    }

    @GetMapping("/{id}")
    public ItemResponseDto getItem(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                   @PathVariable long id) {
        return itemService.getItem(id, ownerId);
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") long ownerId,
                           @Valid @RequestBody ItemDto itemDto) {
        return Mappers.itemToDto(itemService.addItem(Mappers.dtoToItem(itemDto), ownerId));
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") long ownerId,
                              @PathVariable long id,
                              @RequestBody ItemDto itemDto) {
        return Mappers.itemToDto(itemService.updateItem(Mappers.dtoToItem(itemDto), ownerId, id));
    }

    @GetMapping("/search")
    public List<ItemDto> findItemByNameOrDescription(@RequestParam String text) {
        return itemService.getItemByNameOrDescription(text.toLowerCase())
                .stream()
                .map(Mappers::itemToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                 @PathVariable long itemId,
                                 @Valid @RequestBody CommentDto commentDto) {
        return Mappers.commentToDto(itemService.addComment(ownerId, itemId, Mappers.dtoToComment(commentDto)));
    }
}
