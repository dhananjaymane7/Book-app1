package com.books_management.demo.Service;

import com.books_management.demo.Entity.Register;
import com.books_management.demo.Repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private RegisterRepository registerRepository;

    // Get all customers
    public List<Register> getAllCustomers() {
        return registerRepository.findAll();
    }
}
