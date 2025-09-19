package com.books_management.demo.Controller;

import com.books_management.demo.Entity.Order;
import com.books_management.demo.Entity.User;
import com.books_management.demo.Service.OrderService;
import com.books_management.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * Place an order (checkout).
     */
    @PostMapping("/checkout")
    public String placeOrder(@RequestParam Map<String, String> formData, Model model) {

        Long userId = 1L; // TODO: Replace with actual logged-in user id (from session or security context)

        // Validate and parse totalAmount
        String totalAmountStr = cleanAmount(formData.get("totalAmount"));
        if (totalAmountStr == null || totalAmountStr.isEmpty()) {
            throw new IllegalArgumentException("Total Amount is required");
        }
        BigDecimal totalAmount;
        try {
            totalAmount = new BigDecimal(totalAmountStr);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid Total Amount format");
        }

        // Parse discount with default 0
        BigDecimal discount = BigDecimal.ZERO;
        String discountStr = cleanAmount(formData.get("discount"));
        if (discountStr != null && !discountStr.isEmpty()) {
            try {
                discount = new BigDecimal(discountStr);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid Discount format");
            }
        }

        String paymentMethod = formData.get("paymentMethod");
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            throw new IllegalArgumentException("Payment method is required");
        }

        // Build shipping address from multiple fields, ignoring empty parts
        String shippingAddress = String.join(", ",
                filterEmpty(formData.get("address1")),
                filterEmpty(formData.get("address2")),
                filterEmpty(formData.get("city")),
                filterEmpty(formData.get("state")),
                filterEmpty(formData.get("zip")),
                filterEmpty(formData.get("country")));

        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setDiscount(discount);
        order.setPaymentMethod(paymentMethod);
        order.setShippingAddress(shippingAddress);
        order.setStatus("Pending");

        orderService.save(order);

        model.addAttribute("orderId", order.getId());
        return "redirect:/orders/" + order.getId() + "/pay";
    }

    /**
     * Cleans amount string by removing any character except digits and dot.
     * Also validates the format to allow only one decimal point.
     */
    private String cleanAmount(String input) {
        if (input == null) return null;

        // Remove all except digits and decimal points
        String cleaned = input.replaceAll("[^\\d.]", "");

        // Validate there is at most one decimal point
        long dotCount = cleaned.chars().filter(ch -> ch == '.').count();
        if (dotCount > 1) {
            throw new IllegalArgumentException("Invalid amount format: multiple decimal points");
        }

        // Prevent empty or just decimal point strings
        if (cleaned.isEmpty() || cleaned.equals(".")) {
            throw new IllegalArgumentException("Invalid amount format");
        }

        return cleaned;
    }

    /**
     * Helper method to filter out null or empty strings for shipping address.
     */
    private String filterEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        return input.trim();
    }

    /**
     * Show all orders with optional filtering by search and status.
     */
    @GetMapping
    public String viewOrders(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            Model model) {

        List<Order> orders;

        if ((search == null || search.isBlank()) && (status == null || status.isBlank())) {
            orders = orderService.findAll();
        } else {
            orders = orderService.findOrdersWithFilter(search, status);
        }

        model.addAttribute("orders", orders);
        model.addAttribute("search", search);
        model.addAttribute("status", status);

        return "orders"; // your Thymeleaf orders page
    }

    /**
     * View orders for a specific user.
     */
    @GetMapping("/user/{userId}")
    public String viewOrdersByUser(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "error"; // your error page
        }
        List<Order> orders = orderService.findByUser(user);
        model.addAttribute("orders", orders);
        return "orders";
    }

    /**
     * Update order status (for inline editing).
     * This should be called via AJAX.
     */
    @PostMapping("/{orderId}/status")
    @ResponseBody
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        Order order = orderService.findById(orderId);
        if (order == null) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        if (!isValidStatus(status)) {
            return ResponseEntity.badRequest().body("Invalid status");
        }

        order.setStatus(status);
        orderService.save(order);

        return ResponseEntity.ok("Order status updated");
    }

    private boolean isValidStatus(String status) {
        return status != null &&
                (status.equalsIgnoreCase("pending") ||
                        status.equalsIgnoreCase("completed") ||
                        status.equalsIgnoreCase("cancelled"));
    }

}
