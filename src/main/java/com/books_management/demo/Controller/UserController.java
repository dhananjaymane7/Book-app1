package com.books_management.demo.Controller;

import com.books_management.demo.Entity.User;
import com.books_management.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /api/users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // PUT /api/users/{id}/role
    @PutMapping("/{id}/role")
    public ResponseEntity<String> updateRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newRole = body.get("role");

        if (newRole == null || newRole.isEmpty()) {
            return ResponseEntity.badRequest().body("Role must not be empty.");
        }

        try {
            User updatedUser = userService.updateRole(id, newRole.toUpperCase());

            if (updatedUser == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok("Role updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role specified.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating role.");
        }
    }

    // Optional: DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUserById(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
