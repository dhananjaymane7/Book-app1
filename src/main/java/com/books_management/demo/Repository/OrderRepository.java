package com.books_management.demo.Repository;

import com.books_management.demo.Entity.Order;
import com.books_management.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    // Search orders by status and term (id, user id, shipping address)
    @Query("SELECT o FROM Order o WHERE " +
            "(:status IS NULL OR :status = '' OR LOWER(o.status) = LOWER(:status)) AND " +
            "(" +
            "   :search IS NULL OR :search = '' OR " +
            "   CAST(o.id AS string) LIKE %:search% OR " +
            "   CAST(o.user.id AS string) LIKE %:search% OR " +
            "   LOWER(o.shippingAddress) LIKE %:search%" +
            ")")
    List<Order> findBySearchAndStatus(@Param("search") String search, @Param("status") String status);

    List<Order> findByStatus(String status);

    // Search by order id or user id
    @Query("SELECT o FROM Order o WHERE " +
            "CAST(o.id AS string) LIKE %:term% OR " +
            "CAST(o.user.id AS string) LIKE %:term%")
    List<Order> searchByIdOrUser(@Param("term") String term);

    int countByStatus(String status);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o")
    double sumTotalAmount();
}
