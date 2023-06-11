package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.ShortBooking;
import ru.practicum.shareit.request.ItemRequest;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemResponseDto {
    long id;
    String name;
    String description;
    Boolean available;
    ItemRequest request;
    ShortBooking lastBooking;
    ShortBooking nextBooking;

}
