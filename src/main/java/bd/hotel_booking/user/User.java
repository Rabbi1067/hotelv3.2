package bd.hotel_booking.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Every account in the system: guests, staff, admins and the super admin.
 *
 * <p>A single account has exactly one {@link Role}. Accounts are created either
 * by public registration (role = GUEST only) or by the Super Admin
 * (role = STAFF / ADMIN).</p>
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 80, message = "Name must be between 2 and 80 characters")
    @Column(nullable = false, length = 80)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 120, message = "Email must not exceed 120 characters")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 7, max = 20, message = "Phone must be between 7 and 20 characters")
    @Column(nullable = false, length = 20)
    private String phone;

    /**
     * BCrypt-encoded password. Never store the plain text password.
     */
    @NotBlank(message = "Password is required")
    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String avatar;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
