package ru.practicum.shareit.booking.model.enums;

public enum State {
    ALL("All"),
    WAITING("Waiting"),
    CURRENT("Current"),
    PAST("Past"),
    REJECTED("Rejected"),
    FUTURE("Future");

    private final String state;

    State(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
