package com.example.jmetertestapp.config;

import com.example.jmetertestapp.model.Product;
import com.example.jmetertestapp.model.User;
import com.example.jmetertestapp.repository.ProductRepository;
import com.example.jmetertestapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
        initializeProducts();
    }
    
    private void initializeUsers() {
        if (userRepository.count() == 0) {
            List<User> users = Arrays.asList(
                new User("admin", "admin@example.com", "admin123"),
                new User("user1", "user1@example.com", "password123"),
                new User("user2", "user2@example.com", "password123"),
                new User("user3", "user3@example.com", "password123"),
                new User("testuser", "test@example.com", "test123")
            );
            
            users.get(0).setRole(User.Role.ADMIN);
            users.get(0).setFirstName("Admin");
            users.get(0).setLastName("User");
            
            users.get(1).setFirstName("John");
            users.get(1).setLastName("Doe");
            users.get(1).setPhoneNumber("1234567890");
            
            users.get(2).setFirstName("Jane");
            users.get(2).setLastName("Smith");
            users.get(2).setPhoneNumber("0987654321");
            
            users.get(3).setFirstName("Bob");
            users.get(3).setLastName("Johnson");
            users.get(3).setPhoneNumber("5555555555");
            
            users.get(4).setFirstName("Test");
            users.get(4).setLastName("User");
            users.get(4).setPhoneNumber("1111111111");
            
            userRepository.saveAll(users);
            System.out.println("Initialized " + users.size() + " users");
        }
    }
    
    private void initializeProducts() {
        if (productRepository.count() == 0) {
            List<Product> products = Arrays.asList(
                new Product("Laptop", "High-performance laptop", new BigDecimal("999.99"), 50, "Electronics"),
                new Product("Smartphone", "Latest smartphone model", new BigDecimal("699.99"), 100, "Electronics"),
                new Product("Headphones", "Wireless noise-cancelling headphones", new BigDecimal("199.99"), 75, "Electronics"),
                new Product("T-Shirt", "Cotton t-shirt", new BigDecimal("19.99"), 200, "Clothing"),
                new Product("Jeans", "Blue denim jeans", new BigDecimal("49.99"), 150, "Clothing"),
                new Product("Sneakers", "Running shoes", new BigDecimal("79.99"), 80, "Footwear"),
                new Product("Book", "Programming book", new BigDecimal("29.99"), 300, "Books"),
                new Product("Coffee Mug", "Ceramic coffee mug", new BigDecimal("12.99"), 100, "Home"),
                new Product("Backpack", "Travel backpack", new BigDecimal("59.99"), 60, "Accessories"),
                new Product("Watch", "Digital smartwatch", new BigDecimal("299.99"), 40, "Electronics")
            );
            
            products.get(0).setSku("LAPTOP001");
            products.get(1).setSku("PHONE001");
            products.get(2).setSku("HEAD001");
            products.get(3).setSku("TSHIRT001");
            products.get(4).setSku("JEANS001");
            products.get(5).setSku("SNEAK001");
            products.get(6).setSku("BOOK001");
            products.get(7).setSku("MUG001");
            products.get(8).setSku("BAG001");
            products.get(9).setSku("WATCH001");
            
            productRepository.saveAll(products);
            System.out.println("Initialized " + products.size() + " products");
        }
    }
}
