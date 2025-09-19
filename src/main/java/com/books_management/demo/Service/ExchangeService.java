package com.books_management.demo.Service;

import com.books_management.demo.Entity.Exchange;
import com.books_management.demo.Repository.ExchangeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;

    public ExchangeService(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public Exchange saveExchange(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

    public List<Exchange> getAllExchanges() {
        return exchangeRepository.findAll();
    }
}
