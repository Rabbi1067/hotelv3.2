package bd.hotel_booking.content;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Editable website content (hotel name, address, phone, socials, ...).
 * Stored as key/value pairs so new keys do not require a schema change.
 */
@Entity
@Table(name = "site_content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "s_key", nullable = false, unique = true, length = 60)
    private String key;

    @Column(nullable = false, length = 2000)
    private String value;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}