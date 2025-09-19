package com.books_management.demo.Service;
import com.books_management.demo.Entity.Book;
import com.books_management.demo.Entity.WishlistItem;
import com.books_management.demo.Repository.BookRepository;
import com.books_management.demo.Repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final BookRepository bookRepository;
    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistServiceImpl(BookRepository bookRepository, WishlistRepository wishlistRepository) {
        this.bookRepository = bookRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public void addBookToWishlist(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        WishlistItem item = new WishlistItem(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getDescription(),
                book.getCategory()
        );

        wishlistRepository.save(item);
    }


    @Override
    public List<WishlistItem> getPublicWishlist() {
        return wishlistRepository.findAll();
    }

    @Override
    @Transactional
    public void removeBooksFromWishlist(List<Long> bookIds) {
        bookIds.forEach(id -> {
            wishlistRepository.deleteByBookId(id);
        });
    }

    @Override
    @Transactional
    public void removeBookFromWishlist(Long bookId) {
        wishlistRepository.deleteByBookId(bookId);
    }

}
