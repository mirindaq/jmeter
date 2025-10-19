package com.example.jmetertestapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestController {
    
    private final Random random = new Random();
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "Application is running");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
    
    @GetMapping("/delay/{seconds}")
    public ResponseEntity<Map<String, Object>> delay(@PathVariable int seconds) {
        try {
            Thread.sleep(seconds * 1000);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Delayed response after " + seconds + " seconds");
            response.put("timestamp", LocalDateTime.now());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/random-delay")
    public ResponseEntity<Map<String, Object>> randomDelay() {
        int delaySeconds = random.nextInt(5) + 1; // 1-5 seconds
        try {
            Thread.sleep(delaySeconds * 1000);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Random delay of " + delaySeconds + " seconds");
            response.put("delay", delaySeconds);
            response.put("timestamp", LocalDateTime.now());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/error/{statusCode}")
    public ResponseEntity<Map<String, Object>> generateError(@PathVariable int statusCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Generated error with status " + statusCode);
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }
    
    @GetMapping("/random-error")
    public ResponseEntity<Map<String, Object>> randomError() {
        int[] errorCodes = {400, 401, 403, 404, 500, 502, 503};
        int randomErrorCode = errorCodes[random.nextInt(errorCodes.length)];
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Random error with status " + randomErrorCode);
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.valueOf(randomErrorCode));
    }
    
    @PostMapping("/echo")
    public ResponseEntity<Map<String, Object>> echo(@RequestBody(required = false) Object body) {
        Map<String, Object> response = new HashMap<>();
        response.put("echo", body);
        response.put("timestamp", LocalDateTime.now());
        response.put("method", "POST");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/echo")
    public ResponseEntity<Map<String, Object>> echoPut(@RequestBody(required = false) Object body) {
        Map<String, Object> response = new HashMap<>();
        response.put("echo", body);
        response.put("timestamp", LocalDateTime.now());
        response.put("method", "PUT");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/echo")
    public ResponseEntity<Map<String, Object>> echoDelete(@RequestBody(required = false) Object body) {
        Map<String, Object> response = new HashMap<>();
        response.put("echo", body);
        response.put("timestamp", LocalDateTime.now());
        response.put("method", "DELETE");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/load-test")
    public ResponseEntity<Map<String, Object>> loadTest() {
        // Simulate some processing
        int iterations = random.nextInt(1000) + 100;
        for (int i = 0; i < iterations; i++) {
            Math.sqrt(i);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Load test completed");
        response.put("iterations", iterations);
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/memory-test")
    public ResponseEntity<Map<String, Object>> memoryTest() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        Map<String, Object> response = new HashMap<>();
        response.put("totalMemory", totalMemory);
        response.put("freeMemory", freeMemory);
        response.put("usedMemory", usedMemory);
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
