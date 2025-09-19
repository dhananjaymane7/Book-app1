package com.books_management.demo.Repository;

import com.books_management.demo.Entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}
