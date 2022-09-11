package com.dimas.controller;


import com.dimas.api.ApiRegisterRequest;
import com.dimas.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class KeycloakController {

    private final static String PATH = "/kc";
    private final KeycloakService keycloakService;

    @GetMapping(PATH  + "/all")
    public List<UserRepresentation> getAll() {
        return keycloakService.findAll(null, null, null, null, 100);
    }

    @PostMapping(PATH + "/register")
    public UserRepresentation create(@RequestBody ApiRegisterRequest request) {
        return keycloakService.createUser(request.getUserId(), request.getLogin(), request.getPassword());
    }

}
