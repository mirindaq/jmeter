package com.example.jmetertestapp.repository;

import com.example.jmetertestapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByCategory(String category);
    
    List<Product> findByIsActive(Boolean isActive);
    
    List<Product> findByStatus(Product.ProductStatus status);
    
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% OR p.description LIKE %:name%")
    List<Product> findByNameOrDescriptionContaining(@Param("name") String name);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0")
    List<Product> findInStock();
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= :threshold")
    List<Product> findLowStock(@Param("threshold") Integer threshold);
    
    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.isActive = true")
    List<Product> findByCategoryAndActive(@Param("category") String category);
    
    Page<Product> findByCategory(String category, Pageable pageable);
    
    Page<Product> findByIsActive(Boolean isActive, Pageable pageable);
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.isActive = true")
    long countActiveProducts();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.stockQuantity > 0")
    long countInStockProducts();
}
