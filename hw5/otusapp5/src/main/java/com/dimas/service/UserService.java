package com.dimas.service;

import com.dimas.api.ApiUser;
import com.dimas.api.ApiUserRequest;
import com.dimas.data.model.User;
import com.dimas.data.repository.UserRepository;
import com.dimas.exception.NotPermittedAccountException;
import com.dimas.security.UserDetailsStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserDetailsStore userDetailsStore;
    private final KeycloakService keycloakService;

    public ApiUser findById(Long id) {
        validateAccount(id);
        return mapper.map(repository.getById(id));
    }

    @Transactional
    public ApiUser register(ApiUserRequest request) {
        final var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .login(request.getLogin())
                .build();
        final var entity = repository.saveAndFlush(user);
        final var kc = keycloakService.createUser(user.getId(), user.getLogin(), request.getPassword());//throws KeycloakException if failed to create
        return mapper.map(entity).withKcId(kc.getId());
    }

    public void deleteById(Long id) {
        validateAccount(id);
        repository.deleteById(id);
    }

    public ApiUser update(Long id, ApiUserRequest request) {
        validateAccount(id);
        final var user = repository.getById(id);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return mapper.map(repository.save(user));
    }

    public List<ApiUser> getAll() {
        return mapper.map(repository.findAll());
    }

    private void validateAccount(Long id) {
        if (isNull(id) || !id.equals(userDetailsStore.getUserId())) {
            throw new NotPermittedAccountException("You are not allowed to manage account with id=" + id);
        }
    }

    public ApiUser getMe() {
        return findById(userDetailsStore.getUserId());
    }
}
