package bd.hotel_booking.config;

import bd.hotel_booking.content.SiteContent;
import bd.hotel_booking.content.SiteContentRepository;
import bd.hotel_booking.food.FoodCategory;
import bd.hotel_booking.food.FoodItem;
import bd.hotel_booking.food.FoodItemRepository;
import bd.hotel_booking.gallery.GalleryItem;
import bd.hotel_booking.gallery.GalleryRepository;
import bd.hotel_booking.promo.PromoCode;
import bd.hotel_booking.promo.PromoCodeRepository;
import bd.hotel_booking.promo.Promotion;
import bd.hotel_booking.promo.PromotionRepository;
import bd.hotel_booking.room.Room;
import bd.hotel_booking.room.RoomRepository;
import bd.hotel_booking.room.RoomStatus;
import bd.hotel_booking.room.RoomType;
import bd.hotel_booking.wifi.WifiConfig;
import bd.hotel_booking.wifi.WifiConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Seeds the demo catalogue (rooms, menu, promotions, gallery, wifi and editable
 * website content) the first time the application starts against an empty
 * database, so the site is immediately browsable.
 *
 * <p>Every section is idempotent: it checks whether a representative row
 * already exists before inserting, so restarting the app never duplicates
 * anything.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DemoDataSeeder {

    private final RoomRepository roomRepository;
    private final FoodItemRepository foodItemRepository;
    private final PromotionRepository promotionRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final GalleryRepository galleryRepository;
    private final WifiConfigRepository wifiConfigRepository;
    private final SiteContentRepository siteContentRepository;

    private static final String IMG = "https://images.unsplash.com/";
    private static final String Q = "?auto=format&fit=crop&w=700&q=68";

    @Transactional
    public void seed() {
        seedRooms();
        seedFood();
        seedPromotions();
        seedPromoCodes();
        seedGallery();
        seedWifi();
        seedContent();
    }

    private String photo(String key, int w) {
        return IMG + key + "?auto=format&fit=crop&w=" + w + "&q=68";
    }

    /* ---------------- rooms ---------------- */

    private void seedRooms() {
        if (roomRepository.count() > 0) {
            return;
        }
        List<Room> rooms = List.of(
                room("104", "Deluxe King Room", RoomType.DELUXE, 150, 20, 2, "1 King Bed",
                        38, 2, "Garden View", 4.8, 214, true, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Balcony", "Room Service"),
                        List.of(photo("photo-1590490360182-c33d57733427", 900), photo("photo-1590490360182-c33d57733427", 700)),
                        RoomStatus.AVAILABLE),
                room("501", "Premium Ocean Suite", RoomType.SUITE, 320, 15, 3, "1 King + Sofa Bed",
                        62, 5, "Ocean View", 4.9, 138, true, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Pool", "Spa", "Balcony", "Room Service"),
                        List.of(photo("photo-1584132967334-10e028bd69f7", 900), photo("photo-1584132967334-10e028bd69f7", 700)),
                        RoomStatus.AVAILABLE),
                room("312", "Family Terrace Room", RoomType.FAMILY, 190, 0, 5, "2 Queen Beds",
                        52, 3, "Courtyard View", 4.7, 176, true, true,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Balcony", "Room Service"),
                        List.of(photo("photo-1598928506311-c55ded91a20c", 900), photo("photo-1598928506311-c55ded91a20c", 700)),
                        RoomStatus.AVAILABLE),
                room("108", "Classic Double Room", RoomType.DOUBLE, 110, 10, 2, "1 Queen Bed",
                        30, 1, "City View", 4.5, 240, false, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Parking"),
                        List.of(photo("photo-1611892440504-42a792e24d32", 900), photo("photo-1611892440504-42a792e24d32", 700)),
                        RoomStatus.AVAILABLE),
                room("112", "Standard Twin Room", RoomType.TWIN, 105, 0, 2, "2 Single Beds",
                        29, 1, "City View", 4.4, 152, false, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast"),
                        List.of(photo("photo-1618773928121-c32242e63f39", 900), photo("photo-1618773928121-c32242e63f39", 700)),
                        RoomStatus.AVAILABLE),
                room("101", "Cozy Single Room", RoomType.SINGLE, 85, 5, 1, "1 Single Bed",
                        22, 1, "Garden View", 4.3, 98, false, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast"),
                        List.of(photo("photo-1591088398332-8a7791972843", 900), photo("photo-1591088398332-8a7791972843", 700)),
                        RoomStatus.AVAILABLE),
                room("207", "Deluxe Twin Room", RoomType.DELUXE, 165, 0, 3, "2 Double Beds",
                        41, 2, "Pool View", 4.7, 167, false, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Pool", "Room Service"),
                        List.of(photo("photo-1611892440504-42a792e24d32", 900), photo("photo-1611892440504-42a792e24d32", 700)),
                        RoomStatus.AVAILABLE),
                room("408", "Premium Corner View", RoomType.PREMIUM, 260, 25, 3, "1 King + Sofa Bed",
                        55, 4, "City Panorama", 4.9, 121, false, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Gym", "Spa", "Balcony", "Room Service"),
                        List.of(photo("photo-1631049307264-da0ec9d70304", 900), photo("photo-1631049307264-da0ec9d70304", 700)),
                        RoomStatus.AVAILABLE),
                room("404", "Executive Suite", RoomType.SUITE, 285, 0, 3, "1 King Bed",
                        58, 4, "Bay View", 4.8, 204, false, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Gym", "Spa", "Room Service"),
                        List.of(photo("photo-1584132967334-10e028bd69f7", 900), photo("photo-1584132967334-10e028bd69f7", 700)),
                        RoomStatus.AVAILABLE),
                room("218", "Garden Family Suite", RoomType.FAMILY, 210, 0, 6, "3 Double Beds",
                        66, 2, "Garden View", 4.6, 89, false, true,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Pool", "Balcony", "Room Service"),
                        List.of(photo("photo-1598928506311-c55ded91a20c", 900), photo("photo-1598928506311-c55ded91a20c", 700)),
                        RoomStatus.AVAILABLE),
                room("206", "Double Poolside Room", RoomType.DOUBLE, 125, 0, 2, "1 Queen Bed",
                        32, 2, "Pool View", 4.6, 133, false, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Pool", "Room Service"),
                        List.of(photo("photo-1611892440504-42a792e24d32", 900), photo("photo-1611892440504-42a792e24d32", 700)),
                        RoomStatus.AVAILABLE),
                room("601", "Grand Penthouse Suite", RoomType.SUITE, 450, 0, 4, "King + Twin Beds",
                        90, 6, "Rooftop Skyline", 5.0, 74, true, false,
                        List.of("WiFi", "Air Conditioning", "TV", "Breakfast", "Pool", "Gym", "Spa", "Balcony", "Room Service"),
                        List.of(photo("photo-1571003123894-1f0594d2b5d9", 900), photo("photo-1571003123894-1f0594d2b5d9", 700)),
                        RoomStatus.AVAILABLE)
        );
        roomRepository.saveAll(rooms);
        log.info("Seeded {} demo rooms.", rooms.size());
    }

    private Room room(String number, String name, RoomType type, int price, int discount,
                      int capacity, String beds, int size, int floor, String view,
                      double rating, int reviews, boolean featured, boolean petFriendly,
                      List<String> amenities, List<String> images, RoomStatus status) {
        return Room.builder()
                .number(number)
                .name(name)
                .type(type)
                .description("Comfort and refinement come together in the " + name
                        + ". Thoughtful amenities, crisp linens and a serene palette make every stay effortless, "
                        + "while a dedicated team is always a call away to personalise your visit.")
                .price(BigDecimal.valueOf(price))
                .discount(discount)
                .capacity(capacity)
                .beds(beds.equals("2 Single Beds") ? 2 : beds.equals("1 King Bed") ? 1
                        : beds.equals("1 King + Sofa Bed") ? 2 : beds.equals("2 Queen Beds") ? 2
                        : beds.equals("1 Queen Bed") ? 1 : beds.equals("2 Double Beds") ? 2
                        : beds.equals("3 Double Beds") ? 3 : beds.equals("King + Twin Beds") ? 2 : 1)
                .size(size)
                .floor(floor)
                .view(view)
                .rating(rating)
                .reviews(reviews)
                .featured(featured)
                .petFriendly(petFriendly)
                .amenities(amenities)
                .images(images)
                .status(status)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /* ---------------- food menu ---------------- */

    private void seedFood() {
        if (foodItemRepository.count() > 0) {
            return;
        }
        List<FoodItem> items = List.of(
                food(FoodCategory.BREAKFAST, "Continental Breakfast",
                        "Fresh pastries, granola, yogurt and seasonal fruit with coffee or tea.", 18, "photo-1533089860892-a7c6f0a88666"),
                food(FoodCategory.BREAKFAST, "Full English Deluxe",
                        "Eggs, smoked bacon, sausage, grilled tomato, mushrooms and toast.", 24, "photo-1552346154-21d32810aba3"),
                food(FoodCategory.BREAKFAST, "Pancake Stack",
                        "Fluffy buttermilk pancakes with maple syrup, banana and pecans.", 12, "photo-1509042239860-f550ce710b93"),
                food(FoodCategory.LUNCH, "Grilled Salmon",
                        "Atlantic salmon, citrus beurre blanc, asparagus and crushed potatoes.", 26, "photo-1546069901-ba9599a7e63c"),
                food(FoodCategory.LUNCH, "Steak & Crisp Fries",
                        "Prime sirloin char-grilled to order with herb butter and fries.", 29, "photo-1540189549336-e6e99c3679fe"),
                food(FoodCategory.DINNER, "Margherita Pizza",
                        "San Marzano tomato, fresh mozzarella and basil on a wood-fired base.", 16, "photo-1513104890138-7c749659a591"),
                food(FoodCategory.DINNER, "Alfredo Primavera",
                        "Creamy fettuccine with asparagus, peas and parmesan crisp.", 18, "photo-1473093295043-cdd812d0e601"),
                food(FoodCategory.SNACKS, "Cheese Nachos",
                        "Tortilla chips, melted cheddar, jalape\u00f1os, salsa and sour cream.", 10, "photo-1544025162-d76694265947"),
                food(FoodCategory.DRINKS, "Fresh Mint Lemonade",
                        "Hand-pressed lemon, mint and cane sugar over ice.", 5, "photo-1544145945-f90425340c7e"),
                food(FoodCategory.DRINKS, "Espresso Barrel",
                        "A double shot of our single-origin espresso.", 4, "photo-1509042239860-f550ce710b93")
        );
        foodItemRepository.saveAll(items);
        log.info("Seeded {} food items.", items.size());
    }

    private FoodItem food(FoodCategory category, String name, String description, int price, String photoKey) {
        return FoodItem.builder()
                .category(category)
                .name(name)
                .description(description)
                .price(BigDecimal.valueOf(price))
                .image(photo(photoKey, 500))
                .available(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /* ---------------- promotions & promo codes ---------------- */

    private void seedPromotions() {
        if (promotionRepository.count() > 0) {
            return;
        }
        LocalDate today = LocalDate.now();
        List<Promotion> promotions = List.of(
                promotion("Summer Escape",
                        "Book your stay this month and enjoy an exclusive sunset escape with breakfast included on select suites and premium rooms.",
                        new BigDecimal("25"), today.minusDays(10), today.plusDays(14), "SUMMER25", 2, "ACTIVE", true),
                promotion("Autumn Serenity",
                        "Embrace quieter mornings with 15% off garden suites for the upcoming season.",
                        new BigDecimal("15"), today.plusDays(30), today.plusDays(75), "AUTUMN15", 2, "UPCOMING", false),
                promotion("Spring Blossom",
                        "A seasonal welcome for early-year travellers.",
                        new BigDecimal("10"), today.minusDays(70), today.minusDays(40), "SPRING10", 1, "EXPIRED", false)
        );
        promotionRepository.saveAll(promotions);
        log.info("Seeded {} promotions.", promotions.size());
    }

    private Promotion promotion(String title, String description, BigDecimal percent,
                                 LocalDate start, LocalDate end, String code, int minStay,
                                 String status, boolean featured) {
        return Promotion.builder()
                .title(title)
                .description(description)
                .percent(percent)
                .startDate(start)
                .endDate(end)
                .code(code)
                .minStay(minStay)
                .status(status)
                .featured(featured)
                .build();
    }

    private void seedPromoCodes() {
        if (promoCodeRepository.count() > 0) {
            return;
        }
        LocalDate today = LocalDate.now();
        List<PromoCode> codes = List.of(
                code("SUMMER25", "percent", new BigDecimal("25"), BigDecimal.ZERO, today.minusDays(10), today.plusDays(14), 200, 41, "ACTIVE", "Summer Escape: 25% off"),
                code("WELCOME10", "percent", new BigDecimal("10"), BigDecimal.ZERO, today.minusDays(90), today.plusDays(90), 300, 118, "ACTIVE", "Welcome discount for new guests"),
                code("STAY5", "percent", new BigDecimal("5"), new BigDecimal("300"), today.minusDays(20), today.plusDays(30), 150, 36, "ACTIVE", "Extra 5% on bookings over $300"),
                code("LUXEFREE", "fixed", new BigDecimal("50"), new BigDecimal("400"), today.minusDays(5), today.plusDays(40), 60, 9, "ACTIVE", "$50 off stays over $400"),
                code("EXPVR50", "percent", new BigDecimal("50"), BigDecimal.ZERO, today.minusDays(40), today.minusDays(10), 20, 20, "EXPIRED", "Expired flash deal")
        );
        promoCodeRepository.saveAll(codes);
        log.info("Seeded {} promo codes.", codes.size());
    }

    private PromoCode code(String code, String type, BigDecimal value, BigDecimal minAmount,
                           LocalDate start, LocalDate end, int limit, int used,
                           String status, String description) {
        return PromoCode.builder()
                .code(code)
                .type(type)
                .value(value)
                .minAmount(minAmount)
                .startDate(start)
                .endDate(end)
                .usageLimit(limit)
                .used(used)
                .status(status)
                .description(description)
                .build();
    }

    /* ---------------- gallery ---------------- */

    private void seedGallery() {
        if (galleryRepository.count() > 0) {
            return;
        }
        List<GalleryItem> items = List.of(
                gallery("photo-1566073771259-6a8506099945", "Resort exterior", "Exterior"),
                gallery("photo-1551882547-ff40c63fe5fa", "The resort at dusk", "Exterior"),
                gallery("photo-1564501049412-61c2a3083791", "Grand lobby", "Interior"),
                gallery("photo-1571896349842-33c89424de2d", "Infinity pool", "Leisure"),
                gallery("photo-1414235077428-338989a2e8c0", "Ocean-view restaurant", "Dining"),
                gallery("photo-1534438327276-14e5300c3a48", "Fitness centre", "Leisure"),
                gallery("photo-1544161515-4ab6ce6db874", "Serenity spa", "Spa & Wellness"),
                gallery("photo-1470770841072-f978cf4d019e", "Rooftop lounge", "Dining")
        );
        galleryRepository.saveAll(items);
        log.info("Seeded {} gallery items.", items.size());
    }

    private GalleryItem gallery(String photoKey, String label, String category) {
        return GalleryItem.builder()
                .imageUrl(photo(photoKey, 800))
                .label(label)
                .category(category)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /* ---------------- wifi ---------------- */

    private void seedWifi() {
        if (wifiConfigRepository.count() > 0) {
            return;
        }
        wifiConfigRepository.save(WifiConfig.builder()
                .ssid("GrandMeridian")
                .password("Welcome123")
                .instructions("Connect to GrandMeridian and use the password shown at the front desk. "
                        + "The free WiFi covers all rooms and public areas.")
                .updatedAt(LocalDateTime.now())
                .build());
        log.info("Seeded WiFi configuration.");
    }

    /* ---------------- editable site content ---------------- */

    private void seedContent() {
        if (siteContentRepository.count() > 0) {
            return;
        }
        List<SiteContent> contents = List.of(
                content("hotelName", "Grand Meridian Resort"),
                content("tagline", "Where the sea meets serenity"),
                content("address", "Plot 12, Marine Drive, Cox's Bazar 4700, Bangladesh"),
                content("phone", "+880 1711 889900"),
                content("email", "reservations@grandmeridian.com"),
                content("checkInTime", "15:00"),
                content("checkOutTime", "12:00"),
                content("facebook", "https://facebook.com/grandmeridian"),
                content("instagram", "https://instagram.com/grandmeridian"),
                content("twitter", "https://twitter.com/grandmeridian"),
                content("about", "Grand Meridian Resort is a five-star beachfront destination in Cox's Bazar. "
                        + "We blend contemporary comfort with the warmth of Bangladeshi hospitality, "
                        + "offering world-class dining, a serene spa and unforgettable ocean views.")
        );
        siteContentRepository.saveAll(contents);
        log.info("Seeded {} site content entries.", contents.size());
    }

    private SiteContent content(String key, String value) {
        return SiteContent.builder()
                .key(key)
                .value(value)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
