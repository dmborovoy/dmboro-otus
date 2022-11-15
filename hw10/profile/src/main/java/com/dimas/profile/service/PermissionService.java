package com.dimas.profile.service;

import com.dimas.profile.data.model.Permission;
import com.dimas.profile.data.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository repository;

    public Permission findById(Long id) {
        return repository.getById(id);
    }


    public List<Permission> getAll() {
        return repository.findAll();
    }

}
