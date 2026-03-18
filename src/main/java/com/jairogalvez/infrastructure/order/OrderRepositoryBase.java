package com.jairogalvez.infrastructure.order;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class OrderRepositoryBase implements PanacheRepositoryBase<OrderEntity, UUID> {
}
