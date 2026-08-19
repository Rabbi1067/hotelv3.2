package bd.hotel_booking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Central security configuration.
 *
 * <p>Rule of thumb for the URL rules below (each rule is evaluated in order):
 * GUEST accounts may only reach the /user area, STAFF may use the operational
 * pages, ADMIN may also manage content/analytics, and SUPER_ADMIN may do
 * everything including creating Admin/Staff accounts.</p>
 *
 * <p>Because the backend is the source of truth, typing an admin URL manually
 * as a guest always leads to a 403 - the frontend can hide buttons, but only
 * this configuration actually protects the pages.</p>
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Hierarchy: SUPER_ADMIN &gt; ADMIN &gt; STAFF.
     * With this, hasRole("STAFF") is true for STAFF, ADMIN and SUPER_ADMIN.
     *
     * <p>Note: since Spring Security 7 the string-based
     * {@code setHierarchy(...)} was removed. The hierarchy is now built with
     * {@link RoleHierarchyImpl#withDefaultRolePrefix()} (which automatically
     * adds the {@code ROLE_} prefix, so bare role names are used).</p>
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("SUPER_ADMIN").implies("ADMIN")
                .role("ADMIN").implies("STAFF")
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSuccessHandler loginSuccessHandler) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(request -> request

                        // ---- public pages (everyone, logged in or not) ----
                        .requestMatchers(
                                "/", "/home", "/about", "/contact",
                                "/rooms", "/room-details", "/offers", "/booking",
                                "/login", "/register", "/forgot-password", "/reset-password",
                                "/403", "/404",
                                "/css/**", "/js/**", "/img/**", "/images/**",
                                "/scss/**", "/libs/**",
                                "/login.css", "/register.css", "/booking.css", "/admin.css",
                                "/home_scene.js",
                                "/favicon.ico"
                        ).permitAll()

                        // ---- SUPER_ADMIN only: manage admin & staff accounts ----
                        .requestMatchers("/admin/admin-management").hasRole("SUPER_ADMIN")

                        // ---- ADMIN (or SUPER_ADMIN): management/analytics pages ----
                        .requestMatchers(
                                "/admin/analytics", "/admin/settings", "/admin/reports",
                                "/admin/promo-management", "/admin/discount-management",
                                "/admin/gallery-management", "/admin/content-management",
                                "/admin/room-form", "/admin/dashboard", "/admin/user-management"
                        ).hasRole("ADMIN")

                        // ---- STAFF (or ADMIN/SUPER_ADMIN): operational pages ----
                        .requestMatchers("/admin/**").hasRole("STAFF")

                        // ---- any authenticated user (guests too) ----
                        .requestMatchers("/user/**", "/guest/**").authenticated()

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "hv_user")
                        .permitAll()
                )

                .exceptionHandling(handler -> handler
                        .accessDeniedPage("/403")
                );

        return http.build();
    }
}
