package com.dimas.order.data.repository;

import com.dimas.order.data.model.Saga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SagaRepository extends JpaRepository<Saga, UUID> {
}
