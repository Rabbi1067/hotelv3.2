package bd.hotel_booking.user;

/**
 * Lifecycle state of a user account.
 *
 * <ul>
 *   <li>ACTIVE   - the user can log in normally.</li>
 *   <li>INACTIVE - the user can not log in (disabled by an admin).</li>
 *   <li>BLOCKED  - the user can not log in (blocked by an admin).</li>
 * </ul>
 */
public enum UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED
}
