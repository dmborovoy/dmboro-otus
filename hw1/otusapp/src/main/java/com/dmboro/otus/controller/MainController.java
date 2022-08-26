package com.dmboro.otus.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    @GetMapping("/students/{studentName}")
    public String hello(@PathVariable String studentName) {
        return "Hello, " + studentName;
    }

    @GetMapping("/container")
    public String container() {
        return "HOSTNAME=" + System.getenv("HOSTNAME");
    }
}
