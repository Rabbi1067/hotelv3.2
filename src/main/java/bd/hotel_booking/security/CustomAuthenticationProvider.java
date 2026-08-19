package bd.hotel_booking.security;

import bd.hotel_booking.user.User;
import bd.hotel_booking.user.UserService;
import bd.hotel_booking.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tells Spring Security how to verify a "email + password" login.
 *
 * <p>After a successful login the user's roles are mapped into Spring's
 * authority objects (ROLE_GUEST, ROLE_STAFF, ROLE_ADMIN, ROLE_SUPER_ADMIN)
 * which are then used by the URL rules in {@link SecurityConfig}.</p>
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = String.valueOf(authentication.getName());
        String password = String.valueOf(authentication.getCredentials());

        User user = userService.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new DisabledException("This account has been blocked by an administrator.");
        }
        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new DisabledException("This account has been deactivated. Please contact support.");
        }

        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}