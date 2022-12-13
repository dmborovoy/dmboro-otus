package com.dimas.profile.controller;


import com.dimas.profile.api.ApiUser;
import com.dimas.profile.api.ApiUserRequest;
import com.dimas.profile.util.Constant;
import com.dimas.profile.api.ApiPermissionRequest;
import com.dimas.profile.delegate.UserDelegate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(Constant.ROOT_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserDelegate userDelegate;
    private final static String PATH = "/users";

    @GetMapping(PATH + "/{userId}")
    public ApiUser getById(@PathVariable Long userId) {
        return userDelegate.get(userId);
    }

    @GetMapping(PATH)
    public List<ApiUser> getAll() {
        return userDelegate.getAll();
    }

    @PostMapping(PATH)
    public ApiUser create(@RequestBody ApiUserRequest request) {
        return userDelegate.createUser(request);
    }

    @DeleteMapping(PATH + "/{userId}")
    public void delete(@PathVariable Long userId) {
        userDelegate.deleteUserById(userId);
    }

    @PutMapping(PATH+ "/{userId}/permissions")
    public ApiUser addPermission(@PathVariable Long userId, @RequestBody ApiPermissionRequest request) {
        return userDelegate.addPermission(userId, request);
    }

    @DeleteMapping(PATH+ "/{userId}/permissions")
    public ApiUser deletePermission(@PathVariable Long userId, @RequestBody ApiPermissionRequest request) {
        return userDelegate.deletePermission(userId, request);
    }

}
