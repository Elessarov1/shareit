package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.mapper.Mappers;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getOwnerItems(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.getOwnerItems(ownerId)
                .stream()
                .map(Mappers::itemToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable long id) {
        return Mappers.itemToDto(itemService.getItem(id));
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
}
