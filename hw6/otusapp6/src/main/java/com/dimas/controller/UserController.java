package com.dimas.controller;


import com.dimas.api.ApiUser;
import com.dimas.api.ApiUserRequest;
import com.dimas.service.UserService;
import com.dimas.util.CreateGroup;
import com.dimas.util.UpdateGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dimas.util.Constant.ROOT_PATH;

@Slf4j
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class UserController {

    private final static String PATH = "/users";
    private final UserService userService;

    @PostMapping(PATH)
    public ApiUser create(@RequestBody @Validated(CreateGroup.class) ApiUserRequest request) {
        return userService.create(request);
    }

    @GetMapping(PATH + "/{id}")
    public ApiUser getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping(PATH)
    public List<ApiUser> getAll() {
        return userService.getAll();
    }

    @PutMapping(PATH + "/{id}")
    public ApiUser update(@PathVariable Long id, @RequestBody @Validated(UpdateGroup.class) ApiUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping(PATH + "/{id}") // for debug purposes later remove it
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
