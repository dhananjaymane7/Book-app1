package com.books_management.demo.Service;

import com.books_management.demo.Entity.User;
import com.books_management.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findByEmail(String email);
    User findById(Long id);
    List<User> findAllUsers();
    int countUsers();

    boolean deleteUserById(Long id);

    User updateRole(Long id,String role);

}
