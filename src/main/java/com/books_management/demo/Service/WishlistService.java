package com.books_management.demo.Service;

import com.books_management.demo.Entity.WishlistItem;
import java.util.List;

public interface WishlistService {

    /**
     * Adds a book to the public wishlist.
     *
     * @param bookId the ID of the book to add
     */
    void addBookToWishlist(Long bookId);

    /**
     * Retrieves all items in the public wishlist.
     *
     * @return a list of all wishlist items
     */
    List<WishlistItem> getPublicWishlist();

    void removeBooksFromWishlist(List<Long> bookIds);
    void removeBookFromWishlist(Long bookId);
}
