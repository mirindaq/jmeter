package com.example.jmetertestapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JmeterTestAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmeterTestAppApplication.class, args);
    }
}
