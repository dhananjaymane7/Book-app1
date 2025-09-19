package com.books_management.demo.Controller;

import com.books_management.demo.Entity.Book;
import com.books_management.demo.Entity.User;
import com.books_management.demo.Entity.Order;
import com.books_management.demo.Service.BookService;
import com.books_management.demo.Service.UserService;
import com.books_management.demo.Service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class PageController {

    private final UserService userService;
    private final OrderService orderService;

    private final BookService bookService;

    @Autowired
    public PageController(UserService userService, OrderService orderService, BookService bookService) {
        this.userService = userService;
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        System.out.println("Accessing profile page...");

        User user = null;
        List<Order> orders = Collections.emptyList();

        if (principal != null) {
            // Logged-in user case
            user = userService.findByEmail(principal.getName());
        }

        if (user == null) {
            // No logged-in user or user not found: load a default fallback user

            // Replace with an actual email from your database!
            String fallbackEmail = "test@example.com";

            user = userService.findByEmail(fallbackEmail);

            // If fallback user is not found, create a dummy user object for display
            if (user == null) {
                user = new User();
                user.setName("Guest User");
                user.setEmail("guest@example.com");
                user.setRole("USER");
            }
        }

        // Load orders only if user has ID (persisted user)
        if (user.getId() != null) {
            orders = orderService.findByUser(user);
        }

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);

        return "profile";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Order> orders = orderService.findAllOrders(); // fetch orders
        model.addAttribute("orders", orders);
        Iterable<Book> books = bookService.getAllBooks(); // or bookRepository.findAll()
        model.addAttribute("books", books);// add to model
        return "admin-side";
    }

    @GetMapping("/settings")
    public String adminSettings() {
        return "admin/settings";
    }

    @GetMapping("/inventory")
    public String inventory(Model model) {
        Iterable<Book> books = bookService.getAllBooks(); // or bookRepository.findAll()
        model.addAttribute("books", books);// add to model
        return "admin/inventory";
    }

    @GetMapping("/analytics")
    public String analytics() {
        return "admin/analytics";
    }

    @GetMapping("/admin/user-manage")
    public String userManagement() {
        return "admin/user-management";
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "wishlist";
    }

        @GetMapping("/chatbot")
        public String chatbotPage() {
            return "chatbot"; // This should match chatbot.html in src/main/resources/templates/
        }


        @GetMapping("/about")
    public String aboutPage(){
        return "/about";
        }

    @GetMapping("/contact")
    public String contactPage(){
        return "/contact";
    }


}
