package com.dimas.service;

import com.dimas.api.ApiUser;
import com.dimas.api.ApiUserRequest;
import com.dimas.data.model.User;
import com.dimas.data.repository.UserRepository;
import com.dimas.exception.Random400Exception;
import com.dimas.exception.Random500Exception;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final Random random = new Random(42);
    private final static int DELAY_BOUND = 300;//ms
    private final static int FULL_PROBABILITY = 100;//percent
    private final static int ERROR400_PROBABILITY = 10;//percent
    private final static int ERROR500_PROBABILITY = 1;//percent

    public ApiUser findById(Long id) {
        emulateProduction();
        return mapper.map(repository.getById(id));
    }

    public ApiUser create(ApiUserRequest request) {
        emulateProduction();
        final var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        return mapper.map(repository.save(user));
    }

    public void deleteById(Long id) {
        emulateProduction();
        repository.deleteById(id);
    }

    public ApiUser update(Long id, ApiUserRequest request) {
        emulateProduction();
        final var user = repository.getById(id);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return mapper.map(repository.save(user));
    }

    public List<ApiUser> getAll() {
        emulateProduction();
        return mapper.map(repository.findAll());
    }

    @SneakyThrows
    private void emulateProduction() {
        Thread.sleep(random.nextInt(RequestService.DELAY_BOUND));
        if (random.nextInt(FULL_PROBABILITY) <= ERROR400_PROBABILITY) {
            throw new Random400Exception("Random 400 error generated");
        }
        if (random.nextInt(FULL_PROBABILITY) <= ERROR500_PROBABILITY) {
            throw new Random500Exception("Random 500 error generated");
        }
    }
}
