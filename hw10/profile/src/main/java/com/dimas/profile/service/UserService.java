package com.dimas.profile.service;

import com.dimas.profile.api.ApiUserRequest;
import com.dimas.profile.data.model.User;
import com.dimas.profile.data.repository.UserRepository;
import com.dimas.common.UserStatus;
import com.dimas.profile.delegate.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    @Transactional
    public User register(ApiUserRequest request) {
        final var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(UserStatus.NEW)
                .login(request.getLogin())
                .createdOn(LocalDateTime.now())
                .build();
        return repository.save(user);
    }

    public User findById(Long id) {
        return repository.getById(id);
    }


    public User save(User entity) {
        return repository.save(entity);
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
