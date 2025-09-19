package com.books_management.demo.Controller;

import com.books_management.demo.Entity.Book;
import com.books_management.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(name = "search", required = false) String search) {
      Iterable<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "home"; // Return to the home.html template
    }
}
