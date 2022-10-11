package com.dimas.user.controller;


import com.dimas.user.api.ApiUser;
import com.dimas.user.api.ApiUserRequest;
import com.dimas.user.delegate.UserDelegate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dimas.user.util.Constant.ROOT_PATH;

@Slf4j
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserDelegate userDelegate;
    private final static String PATH = "/users";

    @GetMapping(PATH + "/{userId}")
    public ApiUser getById(@PathVariable Long userId) {
        return userDelegate.getUserDetails(userId);
    }

    @GetMapping(PATH)//without details
    public List<ApiUser> getAll(@RequestParam(required = false, defaultValue = "false") Boolean withDetails) {
        if (withDetails) {
            return userDelegate.getAllWithDetails();
        } else {
            return userDelegate.getAll();
        }
    }

    @PostMapping(PATH)//create but do not wait until account will be NORMAL
    public ApiUser create(@RequestBody ApiUserRequest request) {
        return userDelegate.createUser(request);
    }

    @DeleteMapping(PATH + "/{userId}")
    public void delete(@PathVariable Long userId) {
        userDelegate.deleteUserById(userId);
    }
}
