package com.books_management.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

    // Handle user messages
    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return generateResponse(message);
    }

    // Generate responses based on user message
    private String generateResponse(String message) {
        message = message.toLowerCase().trim();

        // Book-related queries
        if (message.contains("book") && message.contains("price")) {
            return "Which book are you looking for? Please provide the title.";
        } else if (message.contains("add") && message.contains("cart")) {
            return "Sure! Which book would you like to add to your cart?";
        } else if (message.contains("checkout") || message.contains("check out")) {
            return "To checkout, go to your cart and click on 'Proceed to Checkout'.";
        } else if ((message.contains("recommend") || message.contains("suggest")) && message.contains("science fiction")) {
            return "If you like science fiction, I recommend *Dune*, *Neuromancer*, and *The Left Hand of Darkness*.";
        } else if ((message.contains("recommend") || message.contains("suggest")) && message.contains("mystery")) {
            return "You might enjoy *Gone Girl* or *The Girl with the Dragon Tattoo* in the mystery category.";
        }

        // Account-related queries
        else if (message.contains("create") && message.contains("account")) {
            return "To create an account, click on the 'Sign Up' button on the top-right and fill in your details.";
        } else if (message.contains("forgot") && message.contains("password")) {
            return "Click on 'Forgot Password' on the login page and follow the instructions.";
        }

        // Help and support
        else if (message.contains("help")) {
            return "I can help you with finding books, checking prices, adding to cart, and more. Ask me anything!";
        }

        // Default fallback
        else {
            return "Sorry, I didn't understand that. Can you please rephrase?";
        }
    }

}
