package com.books_management.demo.Entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"user\"") // Use quotes to handle reserved keyword
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
//    private String role = "USER";
    private String role;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @ManyToMany
    @JoinTable(
            name = "user_wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> wishlist = new HashSet<>();

    // Getters and Setters


    public void setId(Long id) {
        this.id = id;
    }

    public Set<Book> getWishlist() {
        return wishlist;
    }

    public void setWishlist(Set<Book> wishlist) {
        this.wishlist = wishlist;
    }


}
