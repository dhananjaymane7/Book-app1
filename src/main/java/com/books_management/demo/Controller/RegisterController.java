package com.books_management.demo.Controller;

import com.books_management.demo.Entity.Register;
import com.books_management.demo.Service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public String register(@ModelAttribute Register register, RedirectAttributes redirectAttributes) {
        try {
            registerService.registerUser(register);

            // Show success message on login page
            redirectAttributes.addFlashAttribute("success", "Account created successfully. Please log in.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            // Show error message back on register page
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }


    // Optional: render the register page
    @GetMapping
    public String showRegisterForm() {
        return "register"; // This should match the name of your register.html template
    }
}

