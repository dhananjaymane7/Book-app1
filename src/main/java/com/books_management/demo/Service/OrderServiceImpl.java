package com.books_management.demo.Service;

import com.books_management.demo.Entity.Order;
import com.books_management.demo.Entity.User;
import com.books_management.demo.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findOrdersWithFilter(String search, String status) {
        if ((search == null || search.isEmpty()) && (status == null || status.isEmpty())) {
            return orderRepository.findAll();
        }
        return orderRepository.findBySearchAndStatus(search, status);
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        return orderOpt.orElse(null);
    }

    @Override
    public List<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> findByStatusAndSearch(String status, String search) {
        return orderRepository.findBySearchAndStatus(search, status);
    }

    @Override
    public List<Order> searchOrders(String term) {
        return orderRepository.searchByIdOrUser(term);
    }

    @Override
    public int countOrders() {
        return (int) orderRepository.count();
    }

    @Override
    public double sumTotalAmount() {
        return orderRepository.sumTotalAmount();
    }

    @Override
    public int countByStatus(String status) {
        return orderRepository.countByStatus(status);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order saveOrder(BigDecimal totalAmount) {
        Order order = new Order();
        order.setTotalAmount(totalAmount);
        order.setStatus("CREATED");
        // set other fields if needed

        return orderRepository.save(order);
    }
}
