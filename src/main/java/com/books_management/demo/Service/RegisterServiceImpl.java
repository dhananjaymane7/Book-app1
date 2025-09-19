package com.books_management.demo.Service;

import com.books_management.demo.Entity.Register;
import com.books_management.demo.Repository.RegisterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class RegisterServiceImpl implements RegisterService {

    private final RegisterRepository registerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterServiceImpl(RegisterRepository registerRepository, PasswordEncoder passwordEncoder) {
        this.registerRepository = registerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(Register register) {
        if (!register.getPassword().equals(register.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (registerRepository.existsByUsername(register.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (registerRepository.existsByEmail(register.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        register.setPassword(passwordEncoder.encode(register.getPassword()));
        registerRepository.save(register);
    }
}

