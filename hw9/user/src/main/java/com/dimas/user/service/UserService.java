package com.dimas.user.service;

import com.dimas.user.api.ApiUserRequest;
import com.dimas.user.data.model.User;
import com.dimas.user.data.model.UserStatus;
import com.dimas.user.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    public User findById(Long id) {
        return repository.getById(id);
    }

    public User findByAccountId(UUID accountId) {
        return repository.findByAccountId(accountId);
    }

    public User create(ApiUserRequest request) {
        final var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(UserStatus.NEW)
                .build();
        return repository.save(user);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public User update(Long id, ApiUserRequest request) {
        final var user = repository.getById(id);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setStatus(request.getStatus());
        user.setAccountId(request.getAccountId());
        return repository.save(user);
    }

    public User patch(Long id, ApiUserRequest request) {
        final var user = repository.getById(id);
        userMapper.patch(request, user);
        return repository.save(user);
    }

    public User updateStatus(Long id, UserStatus newStatus) {
        final var user = repository.getById(id);
        user.setStatus(newStatus);
        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

}
