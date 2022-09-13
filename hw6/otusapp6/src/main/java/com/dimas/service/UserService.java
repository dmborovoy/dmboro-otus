package com.dimas.service;

import com.dimas.api.ApiUser;
import com.dimas.api.ApiUserRequest;
import com.dimas.data.model.Request;
import com.dimas.data.model.User;
import com.dimas.data.repository.RequestRepository;
import com.dimas.data.repository.UserRepository;
import com.dimas.exception.IdempotenceException;
import com.dimas.filter.HeaderStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RequestRepository requestRepository;
    private final UserMapper mapper;
    private final HeaderStore headerStore;
    private final ObjectMapper objectMapper;

    public ApiUser findById(Long id) {
        return mapper.map(repository.getById(id));
    }

    @Transactional
    public ApiUser create(ApiUserRequest request) {
        validateIdempotence(request);
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

    @SneakyThrows
    public void validateIdempotence(ApiUserRequest request) {//TODO: rework using annotation
        final var currentRequestId = UUID.fromString(headerStore.getRequestId());
        final var requestEntity = requestRepository.findById(currentRequestId);
        if (requestEntity.isPresent()) {
            throw new IdempotenceException("Request already exists with id=" + currentRequestId);
        } else {
            requestRepository.save(Request.builder()
                    .id(currentRequestId)
                    .body(objectMapper.writeValueAsString(request))
                    .createdOn(LocalDateTime.now())
                    .build());
        }

    }
}
