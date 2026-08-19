package bd.hotel_booking.guest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class GuestController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "guest/dashboard";
    }

    @GetMapping("/profile")
    public String profile() {
        return "guest/profile";
    }

    @GetMapping("/my-bookings")
    public String myBookings() {
        return "guest/my-bookings";
    }

    @GetMapping("/favorites")
    public String favorites() {
        return "guest/favorites";
    }

    @GetMapping("/search-rooms")
    public String searchRooms() {
        return "guest/search-rooms";
    }

    @GetMapping("/food-services")
    public String foodServices() {
        return "guest/food-services";
    }

    @GetMapping("/food-cart")
    public String foodCart() {
        return "guest/food-cart";
    }

    @GetMapping("/wifi")
    public String wifi() {
        return "guest/wifi";
    }

    @GetMapping("/payment-status")
    public String paymentStatus() {
        return "guest/payment-status";
    }

    @GetMapping("/money-receipts")
    public String moneyReceipts() {
        return "guest/money-receipts";
    }

    @GetMapping("/notifications")
    public String notifications() {
        return "guest/notifications";
    }

    @GetMapping("/booking-details")
    public String bookingDetails() {
        return "guest/booking-details";
    }

    @GetMapping("/booking-confirmation")
    public String bookingConfirmation() {
        return "guest/booking-confirmation";
    }
}
