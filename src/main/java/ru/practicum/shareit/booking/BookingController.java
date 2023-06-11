package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.mapper.Mappers;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingService bookingService;

    @PostMapping
    public BookingResponseDto createBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                            @Valid @RequestBody BookingRequestDto bookingRequestDto) {
        return Mappers.bookingToResponseDto(
                bookingService.createBooking(
                        Mappers.dtoToBooking(bookingRequestDto), userId, bookingRequestDto.getItemId()));
    }

    @PatchMapping("/{bookingId}")
    public BookingResponseDto approveBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable long bookingId,
                                             @RequestParam boolean approved) {
        return Mappers.bookingToResponseDto(bookingService.approveBooking(userId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public BookingResponseDto getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PathVariable long bookingId) {
        return Mappers.bookingToResponseDto(bookingService.getBooking(userId, bookingId));
    }

    @GetMapping
    public List<BookingResponseDto> getAllBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                                   @RequestParam(
                                                           value = "state", required = false,
                                                           defaultValue = "ALL") String state) {
        return bookingService.getAllBookings(userId, state).stream()
                .map(Mappers::bookingToResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public List<BookingResponseDto> getAllBookingsByOwnerItems(@RequestHeader("X-Sharer-User-Id") long userId,
                                                               @RequestParam(
                                                                       value = "state", required = false,
                                                                       defaultValue = "ALL") String state) {
        return bookingService.getAllBookingsByOwnerItems(userId, state).stream()
                .map(Mappers::bookingToResponseDto)
                .collect(Collectors.toList());
    }
}
