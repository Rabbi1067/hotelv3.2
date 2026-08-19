package bd.hotel_booking.user;

import bd.hotel_booking.user.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    @Transactional
    public User registerGuest(RegisterRequest request) {
        User user = User.builder()
                .name(request.name())
                .email(request.email().trim().toLowerCase())
                .phone(request.phone() == null ? "" : request.phone())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.GUEST)
                .status(UserStatus.ACTIVE)
                .address("")
                .avatar("")
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }
}
