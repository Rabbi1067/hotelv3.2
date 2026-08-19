package bd.hotel_booking.promo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A seasonal offer/promotion shown on the Offers page (e.g. "Summer Escape 25%").
 */
@Entity
@Table(name = "promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal percent;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(length = 30)
    private String code;

    @Column(nullable = false)
    private Integer minStay;

    @Column(nullable = false, length = 20)
    private String status; // ACTIVE, UPCOMING, EXPIRED

    @Column(nullable = false)
    private Boolean featured;
}