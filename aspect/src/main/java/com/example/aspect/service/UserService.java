
package com.example.aspect.service;

import com.example.aspect.annotation.LogExecutionTime;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @LogExecutionTime
    public User findUserById(Long id) {
        simulateProcessingTime(500);
        System.out.println("Service: Finding user with ID: " + id);
        return new User(id, "User" + id, "user" + id + "@example.com");
    }
    
    public void createUser(String username, String email) {
        System.out.println("Service: Creating new user: " + username + " with email: " + email);
        // Implementation omitted
    }
    
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        System.out.println("Service: Deleting user with ID: " + id);
        // Implementation omitted
    }
    
    public String updateUserStatus(Long id, String status) {
        System.out.println("Service: Updating status for user with ID: " + id + " to: " + status);
        // Implementation omitted
        return "Status updated successfully";
    }
    
    private void simulateProcessingTime(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static class User {
        private Long id;
        private String username;
        private String email;
        
        public User(Long id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }
        
        // Getters and setters omitted for brevity
        
        @Override
        public String toString() {
            return "User{id=" + id + ", username='" + username + "', email='" + email + "'}";
        }
    }
}

