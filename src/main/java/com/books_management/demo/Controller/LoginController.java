package com.books_management.demo.Controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {


    @GetMapping("/login")
    public String login() {
        return "login"; // refers to login.html in /templates
    }

}


