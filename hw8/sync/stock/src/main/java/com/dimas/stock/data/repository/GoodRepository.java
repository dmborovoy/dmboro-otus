package com.dimas.stock.data.repository;

import com.dimas.stock.data.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GoodRepository extends JpaRepository<Good, UUID> {
}
