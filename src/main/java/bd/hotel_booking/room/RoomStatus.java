package bd.hotel_booking.room;

/**
 * Current availability state of a room.
 * The frontend uses these exact values to colour the badges.
 */
public enum RoomStatus {
    AVAILABLE,
    RESERVED,
    OCCUPIED,
    MAINTENANCE,
    CLEANING
}
