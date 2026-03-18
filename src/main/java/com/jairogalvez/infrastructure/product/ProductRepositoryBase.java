package com.jairogalvez.infrastructure.product;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class ProductRepositoryBase implements PanacheRepositoryBase<ProductEntity, UUID> {
}