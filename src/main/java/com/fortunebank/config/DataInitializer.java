package com.fortunebank.config;

import com.fortunebank.model.User;

import com.fortunebank.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            String adminPassword = "admin123";

            // Check if admin already exists
            if (userRepository.findByUsername(adminUsername).isEmpty()) {
                User admin = new User();
                admin.setUsername(adminUsername);
                admin.setPasswordHash(passwordEncoder.encode(adminPassword));
                admin.setAdmin(true);
                userRepository.save(admin);
                System.out.println("✅ Default admin user created:");
                System.out.println("   Username: " + adminUsername);
                System.out.println("   Password: " + adminPassword);
            } else {
                System.out.println("✅ Admin user already exists, skipping creation.");
            }
        };
    }
}
