package com.dimas.engine.service;

import com.dimas.engine.api.ApiTransactionRequest;
import com.dimas.engine.data.model.EmailNotification;
import com.dimas.engine.data.repository.EmailNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final EmailNotificationRepository repository;

    public EmailNotification findById(UUID id) {
        return repository.getById(id);
    }

    @Transactional
    public EmailNotification create(ApiTransactionRequest request) {
        final var account = EmailNotification.builder()
                .sender(request.getFrom())
                .recipient(request.getTo())
                .subject(request.getSubject())
                .body(request.getBody())
                .createdOn(LocalDateTime.now())
                .status(request.getStatus())
                .build();
        return repository.save(account);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<EmailNotification> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

}
