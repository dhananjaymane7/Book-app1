package com.books_management.demo.Controller;

import com.books_management.demo.Entity.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckoutController {

//    @GetMapping("/checkout")
//    public String checkout(Model model, HttpSession session) {
//        // Retrieve the cart from session
//        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");
//
//        // If cart is empty, initialize it
//        if (cartItems == null) {
//            cartItems = new ArrayList<>();
//        }
//
//        // Calculate total
//        double itemsTotal = cartItems.stream()
//                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
//                .sum();
//
//        double discount = 50;
//        double tax = 19;
//        double shipping = 0;
//
//        double grandTotal = itemsTotal - discount + tax + shipping;
//
//        model.addAttribute("cartItems", cartItems);
//        model.addAttribute("itemsTotal", itemsTotal);
//        model.addAttribute("discount", discount);
//        model.addAttribute("tax", tax);
//        model.addAttribute("shipping", shipping);
//        model.addAttribute("grandTotal", grandTotal);
//
//        return "checkout"; // Thymeleaf template name (checkout.html)
//    }
@GetMapping("/checkout")
public String checkout(Model model, HttpSession session) {
    List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

    if (cartItems == null) {
        cartItems = new ArrayList<>();
    }

    double itemsTotal = cartItems.stream()
            .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
            .sum();

    double discount = 50;
    double tax = 19;
    double shipping = 0;

    double grandTotal = itemsTotal - discount + tax + shipping;

    // store grandTotal in session for payment use
    session.setAttribute("grandTotal", grandTotal);

    model.addAttribute("cartItems", cartItems);
    model.addAttribute("itemsTotal", itemsTotal);
    model.addAttribute("discount", discount);
    model.addAttribute("tax", tax);
    model.addAttribute("shipping", shipping);
    model.addAttribute("grandTotal", grandTotal);

    return "checkout";
}


    @GetMapping("/orders/checkout")
    public String ordersCheckout(Model model, HttpSession session) {
        return checkout(model, session);
    }
}
