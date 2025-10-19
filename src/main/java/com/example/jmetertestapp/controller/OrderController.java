package com.example.jmetertestapp.controller;

import com.example.jmetertestapp.dto.OrderDTO;
import com.example.jmetertestapp.model.Order;
import com.example.jmetertestapp.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderByOrderNumber(@PathVariable String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber)
                .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/paged")
    public ResponseEntity<Page<OrderDTO>> getOrdersByUserIdPaged(@PathVariable Long userId, Pageable pageable) {
        Page<OrderDTO> orders = orderService.getOrdersByUserId(userId, pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable Order.OrderStatus status) {
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}/paged")
    public ResponseEntity<Page<OrderDTO>> getOrdersByStatusPaged(@PathVariable Order.OrderStatus status, Pageable pageable) {
        Page<OrderDTO> orders = orderService.getOrdersByStatus(status, pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @GetMapping("/amount-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByAmountRange(
            @RequestParam BigDecimal minAmount, 
            @RequestParam BigDecimal maxAmount) {
        List<OrderDTO> orders = orderService.getOrdersByAmountRange(minAmount, maxAmount);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByDateRange(
            @RequestParam LocalDateTime startDate, 
            @RequestParam LocalDateTime endDate) {
        List<OrderDTO> orders = orderService.getOrdersByDateRange(startDate, endDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestParam Order.OrderStatus status) {
        try {
            OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> getOrderCountByStatus(@PathVariable Order.OrderStatus status) {
        long count = orderService.getOrderCountByStatus(status);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @GetMapping("/total-amount/status/{status}")
    public ResponseEntity<BigDecimal> getTotalAmountByStatus(@PathVariable Order.OrderStatus status) {
        BigDecimal totalAmount = orderService.getTotalAmountByStatus(status);
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }
}
