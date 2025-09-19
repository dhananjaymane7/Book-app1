package com.books_management.demo.Repository;

import com.books_management.demo.Entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Register> findByUsername(String username);
}
