package com.books_management.demo.Service;

import com.books_management.demo.Entity.Order;
import com.books_management.demo.Entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> findByUser(User user);
    List<Order> findAll();
    Order save(Order order);
    List<Order> findOrdersWithFilter(String search, String status);
    Order findById(Long id);

    List<Order> findByStatus(String status);
    List<Order> findByStatusAndSearch(String status, String search);
    List<Order> searchOrders(String term);

    int countOrders();
    double sumTotalAmount();
    int countByStatus(String status);
    List<Order> findAllOrders();

    Order saveOrder(BigDecimal totalAmount);


}
