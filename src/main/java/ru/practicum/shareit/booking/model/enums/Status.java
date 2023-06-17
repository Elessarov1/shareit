package ru.practicum.shareit.booking.model.enums;

public enum Status {
    WAITING("Waiting"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    CANCELLED("Cancelled");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
