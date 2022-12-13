package com.dimas.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.dimas.profile", "com.dimas.cqrs", "com.dimas.common"})
public class ProfileApp {
    public static void main(String[] args) {
        SpringApplication.run(ProfileApp.class, args);
    }
}