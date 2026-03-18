package com.jairogalvez.domain.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<Product> findById(UUID id);
    Optional<Product> update(UUID id, Product product);
    Optional<Product> create(Product product);
    Optional<Boolean> delete(UUID id);
    Optional<List<Product>> getAll();
}
