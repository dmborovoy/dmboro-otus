package com.dimas.order.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@FeignClient(name = "account-client", url = "${services.account.url}")
public interface AccountClient {

    @GetMapping("/api/v1/accounts/{accountId}")
    ApiAccount getById(@PathVariable UUID accountId);

    @PutMapping("/api/v1/accounts/{accountId}/debit")
    ApiAccount debit(@PathVariable UUID accountId, @RequestBody ApiDebitRequest request);

    @PutMapping("/api/v1/accounts/{accountId}/credit")
    ApiAccount credit(@PathVariable UUID accountId, @RequestBody ApiCreditRequest request);


    @AllArgsConstructor
    @Builder
    @Data
    @NoArgsConstructor
    class ApiAccount {
        private UUID id;
        private UUID userId;
        private BigDecimal balance;
        private String status;
        private String description;
        private LocalDateTime createdOn;
        private LocalDateTime modifiedOn;
    }

    @AllArgsConstructor
    @Builder
    @Data
    @NoArgsConstructor
    class ApiCreditRequest {
        private UUID userId;
        private BigDecimal amount;
    }

    @AllArgsConstructor
    @Builder
    @Data
    @NoArgsConstructor
    class ApiDebitRequest {
        private UUID userId;
        private BigDecimal amount;
    }
}
