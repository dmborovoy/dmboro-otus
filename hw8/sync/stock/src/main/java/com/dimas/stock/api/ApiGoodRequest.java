package com.dimas.stock.api;

import com.dimas.stock.data.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiGoodRequest {
    private String name;
    private Department department;
    private String description;
    private Integer count;
}
