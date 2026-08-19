package bd.hotel_booking.config;

import bd.hotel_booking.user.Role;
import bd.hotel_booking.user.User;
import bd.hotel_booking.user.UserRepository;
import bd.hotel_booking.user.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Runs once at startup and makes sure the system always has its initial
 * accounts: the Super Admin plus the demo accounts that match the credentials
 * shown on the login screen.
 *
 * <p>The Super Admin is the highest authority in the system. It is created here
 * from the backend (never through the public registration page) with a BCrypt
 * encoded password.</p>
 *
 * <p>By default the credentials come from environment variables so that no
 * secret is committed to the repository:</p>
 * <pre>
 *   SUPER_ADMIN_EMAIL (default superadmin@hotel.com)
 *   SUPER_ADMIN_PASSWORD (default Super123)
 * </pre>
 *
 * <p>The demo accounts (user@hotel.com / admin@hotel.com / staff@hotel.com)
 * are convenience logins for evaluation. Each one is only created when the
 * email is not already taken.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DemoDataSeeder demoDataSeeder;

    @Override
    @Transactional
    public void run(String... args) {
        createSuperAdmin();
        createDemoAccounts();
        demoDataSeeder.seed();
    }

    private void createSuperAdmin() {
        String email = System.getenv().getOrDefault("SUPER_ADMIN_EMAIL", "superadmin@hotel.com");
        String password = System.getenv().getOrDefault("SUPER_ADMIN_PASSWORD", "Super123");

        if (userRepository.existsByEmailIgnoreCase(email)) {
            log.info("Super Admin already exists ({}) - skipping creation.", email);
            return;
        }

        User superAdmin = User.builder()
                .name("System Super Admin")
                .email(email.toLowerCase())
                .phone("+880 1712 665544")
                .password(passwordEncoder.encode(password))
                .role(Role.SUPER_ADMIN)
                .status(UserStatus.ACTIVE)
                .address("Level 6, Grand Meridian, Cox's Bazar")
                .avatar("")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(superAdmin);
        log.info("Super Admin created with email: {}", email);
    }

    /**
     * Seeds the same demo credentials the frontend login screen advertises so
     * that a fresh database is usable immediately.
     */
    private void createDemoAccounts() {
        List<User> demos = List.of(
                demo("Fazley Rabbi", "user@hotel.com", "User123", "+880 1712 445566", Role.GUEST,
                        "12 Gulshan Avenue, Dhaka"),
                demo("Ayesha Karim", "admin@hotel.com", "Admin123", "+880 1711 889900", Role.ADMIN,
                        "Hotel Residence Block, Dhaka"),
                demo("Rahim Chowdhury", "staff@hotel.com", "Staff123", "+880 1813 556677", Role.STAFF,
                        "Front Office Block, Grand Meridian"));

        for (User user : demos) {
            if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
                continue;
            }
            userRepository.save(user);
            log.info("Demo account created: {}", user.getEmail());
        }
    }

    private User demo(String name, String email, String rawPassword, String phone, Role role, String address) {
        return User.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .password(passwordEncoder.encode(rawPassword))
                .role(role)
                .status(UserStatus.ACTIVE)
                .address(address)
                .avatar("")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
