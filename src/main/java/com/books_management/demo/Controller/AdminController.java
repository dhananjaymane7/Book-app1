package com.books_management.demo.Controller;

import com.books_management.demo.Entity.Order;
import com.books_management.demo.Entity.User;
import com.books_management.demo.Service.OrderService;
import com.books_management.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*") // Adjust for security in production
public class AdminController {

    private final OrderService orderService;
    private final UserService userService;

    public AdminController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Endpoint for dashboard summary KPIs like total orders, revenue, users,
     * pending orders.
     * 
     * @return Map of KPI values
     */
    @GetMapping("/dashboard-summary")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {
        int totalOrders = orderService.countOrders();
        double totalRevenue = orderService.sumTotalAmount();
        int activeUsers = userService.countUsers();
        int pendingOrders = orderService.countByStatus("pending");

        Map<String, Object> response = new HashMap<>();
        response.put("totalOrders", totalOrders);
        response.put("totalRevenue", totalRevenue);
        response.put("activeUsers", activeUsers);
        response.put("pendingOrders", pendingOrders);

        return ResponseEntity.ok(response);
    }

    /**
     * Get orders filtered by optional status and/or search term.
     * If no filters are provided, returns all orders.
     * 
     * @param status optional status filter
     * @param search optional search term (order id, user id, address)
     * @return filtered list of orders
     */
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {

        boolean hasStatus = status != null && !status.trim().isEmpty();
        boolean hasSearch = search != null && !search.trim().isEmpty();

        List<Order> orders;

        if (hasStatus && hasSearch) {
            orders = orderService.findByStatusAndSearch(status.toLowerCase(), search.toLowerCase());
        } else if (hasStatus) {
            orders = orderService.findByStatus(status.toLowerCase());
        } else if (hasSearch) {
            orders = orderService.searchOrders(search.toLowerCase());
        } else {
            orders = orderService.findAllOrders();
        }

        return ResponseEntity.ok(orders);
    }

    /**
     * Fetch all users in the system.
     * 
     * @return list of users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Fetch customers for admin table with password omitted.
     */
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> customers = userService.findAllUsers().stream()
                .map(u -> new CustomerDto(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole(),
                        null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    public static class CustomerDto {
        private Long id;
        private String name;
        private String email;
        private String role;
        private LocalDateTime createdAt;

        public CustomerDto(Long id, String name, String email, String role, LocalDateTime createdAt) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
            this.createdAt = createdAt;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
