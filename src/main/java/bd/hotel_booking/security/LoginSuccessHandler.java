package bd.hotel_booking.security;

import bd.hotel_booking.user.User;
import bd.hotel_booking.user.UserService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Runs right after a successful login.
 *
 * <p>It writes the small {@code hv_user} cookie (used by the frontend to know
 * who is logged in and to draw the right menu), then redirects the user to the
 * dashboard that matches their role:</p>
 *
 * <ul>
 *   <li>SUPER_ADMIN / ADMIN -> /admin/dashboard</li>
 *   <li>STAFF               -> /admin/booking-management</li>
 *   <li>GUEST               -> /user/dashboard</li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request,
                                        jakarta.servlet.http.HttpServletResponse response,
                                        Authentication authentication) throws java.io.IOException {

        String email = authentication.getName();
        User user = userService.findByEmail(email);
        String role = (user != null && user.getRole() != null) ? user.getRole().name() : "GUEST";
        String name = (user != null && user.getName() != null) ? user.getName() : email;

        String payload = "{\"id\":" + (user != null ? user.getId() : 0)
                + ",\"name\":\"" + escapeJson(name)
                + "\",\"email\":\"" + escapeJson(email)
                + "\",\"role\":\"" + role + "\"}";

        Cookie cookie = new Cookie("hv_user", URLEncoder.encode(payload, StandardCharsets.UTF_8));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); // 7 days
        cookie.setHttpOnly(false);          // readable by the frontend JS
        response.addCookie(cookie);

        String target = switch (role) {
            case "SUPER_ADMIN", "ADMIN" -> "/admin/dashboard";
            case "STAFF" -> "/admin/booking-management";
            default -> "/user/dashboard";
        };
        response.sendRedirect(target);
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
