package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerIdOrderByStartDesc(long bookerId);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(
            long bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(long bookerId, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(long bookerId, LocalDateTime start);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(long bookerId, Status status);

    List<Booking> findAllByBookerIdAndItemIdAndEndBefore(long bookerId, long itemId, LocalDateTime end);

    @Query("select b " +
            "from Booking as b " +
            "where b.item.id in (" +
            "select i.id " +
            "from Item as i " +
            "where i.owner.id = ?1) " +
            "order by b.start desc ")
    List<Booking> findAllByOwnerIdOrderByStartDesc(long ownerId);

    @Query("select b " +
            "from Booking as b " +
            "where b.item.id in (" +
            "select i.id " +
            "from Item as i " +
            "where i.owner.id = ?1) " +
            "and b.start < ?2 and b.end > ?3 " +
            "order by b.start desc ")
    List<Booking> findAllByOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(
            long ownerId, LocalDateTime start, LocalDateTime end);

    @Query("select b " +
            "from Booking as b " +
            "where b.item.id in (" +
            "select i.id " +
            "from Item as i " +
            "where i.owner.id = ?1) " +
            "and b.end < ?2 " +
            "order by b.start desc ")
    List<Booking> findAllByOwnerIdAndEndBeforeOrderByStartDesc(long ownerId, LocalDateTime end);

    @Query("select b " +
            "from Booking as b " +
            "where b.item.id in (" +
            "select i.id " +
            "from Item as i " +
            "where i.owner.id = ?1) " +
            "and b.start > ?2 " +
            "order by b.start desc ")
    List<Booking> findAllByOwnerIdAndStartAfterOrderByStartDesc(long bookerId, LocalDateTime start);

    @Query("select b " +
            "from Booking as b " +
            "where b.item.id in (" +
            "select i.id " +
            "from Item as i " +
            "where i.owner.id = ?1) " +
            "and b.status = ?2 " +
            "order by b.start desc ")
    List<Booking> findAllByOwnerIdAndStatusOrderByStartDesc(long bookerId, Status status);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "where i.owner.id = ?1 " +
            "and i.id in (?2) " +
            "order by b.id desc")
    List<Booking> findAllByOwnerIdAndItemIds(long ownerId, List<Long> itemIds);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "where i.owner.id = ?1 " +
            "and i.id = ?2 " +
            "order by b.start desc ")
    List<Booking> findAllByOwnerIdAndItemId(long ownerId, long itemId);
}
