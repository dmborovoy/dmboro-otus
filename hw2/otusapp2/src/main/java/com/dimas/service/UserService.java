package com.dimas.service;

import com.dimas.api.ApiUser;
import com.dimas.api.ApiUserRequest;
import com.dimas.data.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.dimas.data.repository.UserRepository;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public ApiUser findById(Long id) {
        return mapper.map(repository.getById(id));
    }

    public ApiUser create(ApiUserRequest request) {
        final var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        return mapper.map(repository.save(user));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ApiUser update(Long id, ApiUserRequest request) {
        final var user = repository.getById(id);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return mapper.map(repository.save(user));
    }

    public List<ApiUser> getAll() {
        return mapper.map(repository.findAll());
    }
}
