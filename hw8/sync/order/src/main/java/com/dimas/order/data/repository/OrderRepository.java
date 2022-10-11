package com.dimas.order.data.repository;

import com.dimas.order.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Order findByTransactionId(UUID transactionId);
}
