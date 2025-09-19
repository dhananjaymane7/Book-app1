package com.books_management.demo.Service;

import com.books_management.demo.Entity.Book;
import com.books_management.demo.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    // Constructor injection for BookRepository
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Add a new book to the database
    @Override
    public boolean addBook(Book book) {
        try {
            bookRepository.save(book); // Save the book to the database
            return true; // Return true if the book is added successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there's an exception
        }
    }

    // Update an existing book
    @Override
    public boolean updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBookOptional = bookRepository.findById(id);
        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setDescription(updatedBook.getDescription());
            existingBook.setCategory(updatedBook.getCategory());
            existingBook.setStock(updatedBook.getStock());
            bookRepository.save(existingBook); // Save the updated book to the database
            return true;
        } else {
            return false; // Book not found
        }
    }

    // Delete a book by ID
    @Override
    public boolean deleteBook(Long id) {
        try {
            bookRepository.deleteById(id); // Delete the book by ID
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there's an exception
        }
    }

    // Get book by ID
    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id); // Return an Optional<Book> if found
    }

    // Get all books
    @Override
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll(); // Return all books from the database
    }

    @Override
    public String getBookImage(String bookTitle) {
        String imageUrl = fetchBookImageFromWikipedia(bookTitle);
        return imageUrl != null ? imageUrl : null;
    }

    // Fetch the book image from Wikipedia API
    private String fetchBookImageFromWikipedia(String bookTitle) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl("https://en.wikipedia.org/w/api.php")
                .queryParam("action", "query")
                .queryParam("format", "json")
                .queryParam("prop", "pageimages")
                .queryParam("titles", bookTitle)
                .queryParam("pithumbsize", "500")
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        return parseImageUrlFromResponse(response);
    }

    // Parse the image URL from Wikipedia's response
    private String parseImageUrlFromResponse(String response) {
        if (response.contains("thumburl")) {
            int start = response.indexOf("thumburl\":\"") + 11;
            int end = response.indexOf("\"", start);
            return response.substring(start, end);
        }
        return null; // Return null if no image is found
    }
}
