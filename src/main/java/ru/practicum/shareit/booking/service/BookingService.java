package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking, long userId, long itemId);

    Booking approveBooking(long userId, long bookingId, boolean approved);

    Booking getBooking(long userId, long bookingId);

    List<Booking> getAllBookings(long userId, String state);

    List<Booking> getAllBookingsByOwnerItems(long userId, String state);
}
