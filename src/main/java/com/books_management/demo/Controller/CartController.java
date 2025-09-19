package com.books_management.demo.Controller;

import com.books_management.demo.Entity.Book;
import com.books_management.demo.Entity.CartItem;
import com.books_management.demo.Service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private BookService bookService;

    // Add to Cart functionality
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("bookId") Long bookId, HttpSession session) {
        Book book = bookService.getBookById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        // Get cart from session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Check if the book is already in the cart
        boolean bookExists = false;
        for (CartItem item : cart) {
            if (item.getBook().getId().equals(bookId)) {
                item.setQuantity(item.getQuantity() + 1);  // Increase quantity
                bookExists = true;
                break;
            }
        }

        // If book not in cart, add it with quantity 1
        if (!bookExists) {
            cart.add(new CartItem(book, 1));
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart"; // Redirect to cart page
    }

    // Update Cart Quantity functionality
    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam("cartItemId") Long cartItemId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            // Find the cart item and update the quantity
            for (CartItem item : cart) {
                if (item.getBook().getId().equals(cartItemId)) {
                    item.setQuantity(quantity);  // Update quantity
                    break;
                }
            }
            session.setAttribute("cart", cart); // Update session with new cart
        }
        return "redirect:/cart";  // Redirect back to cart page
    }

    // Remove Item from Cart functionality
    @GetMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("id") Long cartItemId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            // Remove the item from the cart using the book's ID
            cart.removeIf(item -> item.getBook().getId().equals(cartItemId));
            session.setAttribute("cart", cart);  // Update session with the modified cart
        }
        return "redirect:/cart";  // Redirect to cart page
    }

    // Display Cart functionality
    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        // Retrieve the cart from session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();  // If cart is empty, initialize an empty cart
        }

        // Calculate the total price of the cart
        double totalPrice = cart.stream().mapToDouble(item -> item.getBook().getPrice() * item.getQuantity()).sum();

        // Add the cart items and total price to the model
        model.addAttribute("cartItems", cart);
        model.addAttribute("totalPrice", totalPrice);

        return "cart"; // Return to cart.html page
    }
}
