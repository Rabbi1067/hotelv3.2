package bd.hotel_booking.wifi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The hotel-wide WiFi configuration.
 */
@Entity
@Table(name = "wifi_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WifiConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String ssid;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(length = 300)
    private String instructions;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}