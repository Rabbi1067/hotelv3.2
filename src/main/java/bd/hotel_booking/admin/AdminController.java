package bd.hotel_booking.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/booking-management")
    public String bookingManagement() {
        return "admin/booking-management";
    }

    @GetMapping("/room-management")
    public String roomManagement() {
        return "admin/room-management";
    }

    @GetMapping("/room-form")
    public String roomForm() {
        return "admin/room-form";
    }

    @GetMapping("/payment-management")
    public String paymentManagement() {
        return "admin/payment-management";
    }

    @GetMapping("/user-management")
    public String userManagement() {
        return "admin/user-management";
    }

    @GetMapping("/admin-management")
    public String adminManagement() {
        return "admin/admin-management";
    }

    @GetMapping("/promo-management")
    public String promoManagement() {
        return "admin/promo-management";
    }

    @GetMapping("/discount-management")
    public String discountManagement() {
        return "admin/discount-management";
    }

    @GetMapping("/gallery-management")
    public String galleryManagement() {
        return "admin/gallery-management";
    }

    @GetMapping("/content-management")
    public String contentManagement() {
        return "admin/content-management";
    }

    @GetMapping("/reports")
    public String reports() {
        return "admin/reports";
    }

    @GetMapping("/analytics")
    public String analytics() {
        return "admin/analytics";
    }

    @GetMapping("/wifi-management")
    public String wifiManagement() {
        return "admin/wifi-management";
    }

    @GetMapping("/food-management")
    public String foodManagement() {
        return "admin/food-management";
    }

    @GetMapping("/check-ins")
    public String checkIns() {
        return "admin/check-ins";
    }

    @GetMapping("/check-outs")
    public String checkOuts() {
        return "admin/check-outs";
    }

    @GetMapping("/settings")
    public String settings() {
        return "admin/settings";
    }
}
