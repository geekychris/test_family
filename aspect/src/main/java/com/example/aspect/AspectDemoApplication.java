
package com.example.aspect;

import com.example.aspect.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AspectDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AspectDemoApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner demoAspects(UserService userService) {
        return args -> {
            System.out.println("\n----- Demonstrating AspectJ Weaving -----\n");
            
            // Demonstrate @LogExecutionTime annotation
            System.out.println("Testing @LogExecutionTime aspect:");
            userService.findUserById(42L);
            
            // Demonstrate method logging and parameter inspection
            System.out.println("\nTesting before/after advice with parameters:");
            userService.createUser("john_doe", "john@example.com");
            
            // Demonstrate exception handling
            System.out.println("\nTesting exception handling in aspect:");
            try {
                userService.deleteUser(null);
            } catch (IllegalArgumentException e) {
                System.out.println("Caught expected exception: " + e.getMessage());
            }
            
            // Demonstrate around advice
            System.out.println("\nTesting around advice:");
            String result = userService.updateUserStatus(123L, "ACTIVE");
            System.out.println("Result from service: " + result);
            
            System.out.println("\n----- AspectJ Weaving Demo Complete -----");
        };
    }
}

