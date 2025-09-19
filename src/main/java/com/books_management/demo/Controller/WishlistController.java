package com.books_management.demo.Controller;

import com.books_management.demo.Service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public String addToWishlist(@RequestParam Long bookId) {
        wishlistService.addBookToWishlist(bookId);
        return "redirect:/wishlist/view"; // Redirect to the public wishlist page
    }

    @GetMapping("/view")
    public String viewWishlist(Model model) {
        model.addAttribute("wishlist", wishlistService.getPublicWishlist());
        return "wishlist"; // Thymeleaf template to display the wishlist
    }

    @PostMapping("/remove-selected")
    public String removeSelected(@RequestParam(value = "selectedItems", required = false) String selectedItemsStr) {
        if (selectedItemsStr != null && !selectedItemsStr.trim().isEmpty()) {
            List<Long> selectedItems = Arrays.stream(selectedItemsStr.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            if (!selectedItems.isEmpty()) {
                wishlistService.removeBooksFromWishlist(selectedItems);
            }
        }
        return "redirect:/wishlist/view";
    }


    @PostMapping("/remove")
    public String removeBook(@RequestParam("bookId") Long bookId,
                             RedirectAttributes redirectAttributes) {
        if (bookId != null) {
            wishlistService.removeBookFromWishlist(bookId);
            redirectAttributes.addFlashAttribute("message", "Book removed from wishlist.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid book ID.");
        }
        return "redirect:/wishlist/view";
    }



}
