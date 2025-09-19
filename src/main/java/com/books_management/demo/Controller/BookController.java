package com.books_management.demo.Controller;

import com.books_management.demo.Entity.Book;
import com.books_management.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class    BookController {

    @Autowired
    private BookService bookService;

    // Show the form to add a new book
    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book"; // Thymeleaf template for adding books
    }

    // Handle the form submission and add the book
    @PostMapping("/books/add")
    public String addBook(Book book, Model model, RedirectAttributes redirectAttributes) {
        boolean isAdded = bookService.addBook(book);  // Assuming addBook handles saving the book to the database

        if (isAdded) {
            redirectAttributes.addFlashAttribute("success", "Book added successfully!");
            return "redirect:/books"; // Redirect to the books list page after successful addition
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to add the book. Please try again.");
            return "redirect:/books/add"; // Stay on the add-book page if failed
        }
    }

    @GetMapping("/books")
    public String showAllBooks(Model model)
    {
        Iterable<Book> books=bookService.getAllBooks();
        for (Book book : books) {
            String imageUrl = bookService.getBookImage(book.getTitle());
            book.setImageUrl(imageUrl);  // Set the image URL to the book object
        }
        model.addAttribute("books",books);
        return "book-list";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id).orElse(null);
        if (book != null) {
            model.addAttribute("book", book);
            return "edit-book"; // Thymeleaf template for editing book
        }
        model.addAttribute("error", "Book not found");
        return "book-list";
    }

    @PostMapping("/books/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Book updatedBook, Model model) {
        boolean isUpdated = bookService.updateBook(id, updatedBook);
        if (isUpdated) {
            model.addAttribute("success", "Book updated successfully");
        } else {
            model.addAttribute("error", "Failed to update the book");
        }
        return "redirect:/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, Model model) {
        boolean isDeleted = bookService.deleteBook(id);
        if (isDeleted) {
            model.addAttribute("success", "Book deleted successfully");
        } else {
            model.addAttribute("error", "Failed to delete the book");
        }
        return "redirect:/books";
    }

}
