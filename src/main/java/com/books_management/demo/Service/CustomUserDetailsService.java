package com.books_management.demo.Service;

import com.books_management.demo.Entity.Register;
import com.books_management.demo.Repository.RegisterRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private final RegisterRepository registerRepository;

    public CustomUserDetailsService(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Register user = registerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        // Note: password stored in DB must be encoded with BCrypt

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")   // or your roles if you have
                .build();
    }
}
