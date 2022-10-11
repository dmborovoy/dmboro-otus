package com.dimas.stock.api;


import com.dimas.stock.data.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiGood {
    private UUID id;
    private String name;
    private Department department;
    private String description;
    private Integer count;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
}
