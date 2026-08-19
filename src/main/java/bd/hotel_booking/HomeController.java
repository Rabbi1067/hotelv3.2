package bd.hotel_booking;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "public/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "public/contact";
    }

    @GetMapping("/rooms")
    public String rooms() {
        return "public/rooms";
    }

    @GetMapping("/room-details")
    public String roomDetails() {
        return "public/room-details";
    }

    @GetMapping("/offers")
    public String offers() {
        return "public/offers";
    }

    @GetMapping("/booking")
    public String booking() {
        return "public/booking";
    }

    @GetMapping("/403")
    public String forbidden() {
        return "public/403";
    }

    @GetMapping("/404")
    public String notFound() {
        return "public/404";
    }
}
