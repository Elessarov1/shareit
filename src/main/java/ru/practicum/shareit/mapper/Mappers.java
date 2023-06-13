package ru.practicum.shareit.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.ShortBooking;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class Mappers {

    public ItemDto itemToDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .request(item.getRequest() != null ? item.getRequest() : null)
                .build();
    }

    public ItemResponseDto itemToResponseDto(Item item, List<Booking> bookingList, List<CommentResponseDto> comments) {
        ShortBooking lastShortBooking = null;
        ShortBooking nextShortBooking = null;
        if (!bookingList.isEmpty()) {
            LocalDateTime currentTime = LocalDateTime.now();
            Optional<Booking> lastBooking = bookingList.stream()
                    .filter(b -> b.getItem().getId() == item.getId() && b.getStatus().equals(Status.APPROVED))
                    .filter(b -> (b.getStart().isBefore(currentTime) && b.getEnd().isAfter(currentTime))
                            || b.getEnd().isBefore(currentTime))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .findFirst();

            Optional<Booking> nextBooking = bookingList.stream()
                    .filter(b -> b.getItem().getId() == item.getId() && b.getStatus().equals(Status.APPROVED))
                    .sorted(Comparator.comparing(Booking::getStart))
                    .filter(b -> b.getStart().isAfter(currentTime))
                    .findFirst();
            if (lastBooking.isPresent())
                lastShortBooking = bookingToShortBooking(lastBooking.get());
            if (nextBooking.isPresent())
                nextShortBooking = bookingToShortBooking(nextBooking.get());
        }
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .request(item.getRequest() != null ? item.getRequest() : null)
                .lastBooking(lastShortBooking)
                .nextBooking(nextShortBooking)
                .comments(comments)
                .build();
    }

    public Item dtoToItem(ItemDto itemDto) {
        return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .request(itemDto.getRequest())
                .build();
    }

    public UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User dtoToUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public Booking dtoToBooking(BookingRequestDto bookingRequestDto) {
        return Booking.builder()
                .start(bookingRequestDto.getStart())
                .end(bookingRequestDto.getEnd())
                .status(Status.WAITING)
                .build();
    }

    public ShortBooking bookingToShortBooking(Booking booking) {
        return ShortBooking.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .bookerId(booking.getBooker().getId())
                .build();
    }

    public BookingResponseDto bookingToResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .itemId(booking.getItem().getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(new BookingResponseDto.Item(booking.getItem().getId(), booking.getItem().getName()))
                .booker(new BookingResponseDto.User(booking.getBooker().getId(), booking.getBooker().getName()))
                .status(booking.getStatus())
                .build();
    }

    public CommentResponseDto commentToDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .item(new CommentResponseDto.Item(comment.getItem().getId(), comment.getItem().getName()))
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public Comment dtoToComment(CommentRequestDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .build();
    }

    public List<BookingResponseDto> allBookingsToDto(List<Booking> bookings) {
        return bookings.stream()
                .map(Mappers::bookingToResponseDto)
                .collect(Collectors.toList());
    }
}
