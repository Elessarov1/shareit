package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.ShortBooking;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.util.List;


@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String name;

    @Column
    String description;

    @Column
    Boolean available;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    User owner;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    ItemRequest request;

    @Transient
    ShortBooking lastBooking;

    @Transient
    ShortBooking nextBooking;

    @Transient
    List<CommentResponseDto> comments;

}
