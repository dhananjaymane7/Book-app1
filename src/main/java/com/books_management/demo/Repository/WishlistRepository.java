package com.books_management.demo.Repository;

import com.books_management.demo.Entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    void deleteByBookId(Long bookId);
    void deleteByBookIdIn(List<Long> bookIds);
}
