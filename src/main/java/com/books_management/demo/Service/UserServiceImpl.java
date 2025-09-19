package com.books_management.demo.Service;

import com.books_management.demo.Entity.User;
import com.books_management.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAllUsers() {
        Iterable<User> iterable = userRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public int countUsers() {
        return 0;
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User updateRole(Long id, String role) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;

        // Optional: Validate allowed roles
        List<String> allowedRoles = List.of("USER", "MODERATOR", "ADMIN");
        if (!allowedRoles.contains(role.toUpperCase())) {
            throw new IllegalArgumentException("Invalid role");
        }

        user.setRole(role.toUpperCase());
        return userRepository.save(user);
    }

    public int countOrders() {
        return (int) userRepository.count();
    }

}
