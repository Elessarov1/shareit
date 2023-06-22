package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemShortResponseDto;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class RequestItemResponseDto {
    long id;
    String description;
    LocalDateTime created;
    List<ItemShortResponseDto> items;
}
