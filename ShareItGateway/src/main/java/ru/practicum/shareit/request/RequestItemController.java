package ru.practicum.shareit.request;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestItemDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class RequestItemController {
    RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader(name = "X-Sharer-User-Id") @NotNull Long ownerId,
                                             @Valid @RequestBody RequestItemDto request) {
        return requestClient.addRequest(ownerId, request);
    }

    @GetMapping
    public ResponseEntity<Object> getOwnerRequests(@RequestHeader("X-Sharer-User-Id") @NotNull Long ownerId) {
        return requestClient.getOwnerRequests(ownerId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader("X-Sharer-User-Id") @NotNull Long ownerId,
                                                       @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                       @RequestParam(defaultValue = "10") @Positive int size) {
        return requestClient.getAllRequests(ownerId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@RequestHeader("X-Sharer-User-Id") @NotNull Long ownerId,
                                                 @PathVariable long requestId) {
        return requestClient.getRequestById(ownerId, requestId);
    }
}
