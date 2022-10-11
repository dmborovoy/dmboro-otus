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

import java.time.LocalDateTime;
import java.util.UUID;

@FeignClient(name = "stock-client", url = "${services.stock.url}")
public interface StockClient {

    @GetMapping("/api/v1/goods/{goodId}")
    ApiGood getById(@PathVariable UUID goodId);

    @PutMapping("/api/v1/goods/{goodId}/reserve")
    ApiGood reserve(@PathVariable UUID goodId, @RequestBody ApiReserveRequest request);

    @PutMapping("/api/v1/goods/{goodId}/unreserve")
    ApiGood unreserve(@PathVariable UUID goodId, @RequestBody ApiReserveRequest request);

    @AllArgsConstructor
    @Builder
    @Data
    @NoArgsConstructor
    class ApiGood {
        private UUID id;
        private String name;
        private Department department;
        private String description;
        private Integer count;
        private LocalDateTime createdOn;
        private LocalDateTime modifiedOn;
    }

    @AllArgsConstructor
    @Builder
    @Data
    @NoArgsConstructor
    class ApiReserveRequest {
        private Integer count;
    }

    enum Department {
        SHOP, STOCK
    }
}
