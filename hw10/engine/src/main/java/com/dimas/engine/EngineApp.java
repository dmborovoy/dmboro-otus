package com.dimas.engine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.dimas.notification", "com.dimas.cqrs"})
public class EngineApp {
    public static void main(String[] args) {
        SpringApplication.run(EngineApp.class, args);
    }
}