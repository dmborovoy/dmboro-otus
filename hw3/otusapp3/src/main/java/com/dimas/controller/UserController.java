package com.dimas.controller;


import com.dimas.api.ApiUser;
import com.dimas.api.ApiUserRequest;
import com.dimas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public ApiUser getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/users")
    public List<ApiUser> getAll() {
        return userService.getAll();
    }

    @PostMapping("/users")
    public ApiUser create(@RequestBody ApiUserRequest request) {
        return userService.create(request);
    }

    @PutMapping("/users/{id}")
    public ApiUser create(@PathVariable Long id, @RequestBody ApiUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
