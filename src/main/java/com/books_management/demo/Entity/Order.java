package com.books_management.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Remove this userId field OR mark it properly as read-only
    // Option 1: Remove userId field entirely and use 'user' association only
    // private int userId;

    private BigDecimal totalAmount;
    private BigDecimal discount;
    private String status;
    private String paymentMethod;
    private String shippingAddress;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
    // If you really want userId as well, use this pattern:

    @Column(name = "user_id", insertable = false, updatable = false)
    public int getUserId() {
        return user != null ? user.getId() : 0;
    }
    */

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", discount=" + discount +
                ", status='" + status + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }

    
}
