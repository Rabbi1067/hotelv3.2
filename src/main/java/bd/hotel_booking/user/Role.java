package bd.hotel_booking.user;

/**
 * The four access levels in the system, from lowest to highest:
 *
 * <ul>
 *   <li>GUEST       - a normal hotel customer who registered publicly.</li>
 *   <li>STAFF       - hotel employee (created by the Super Admin).</li>
 *   <li>ADMIN       - manager (created by the Super Admin).</li>
 *   <li>SUPER_ADMIN - the top-level system owner (created once at startup).</li>
 * </ul>
 *
 * The same value is used for the database column and for Spring Security
 * authorities (prefixed with "ROLE_" when used in security rules).
 */
public enum Role {
    GUEST,
    STAFF,
    ADMIN,
    SUPER_ADMIN
}
