package com.jairogalvez.domain.order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> create(Order order);
    Optional<Order> findById(UUID id);
    Optional<Order> update(UUID id, Order order);
}
