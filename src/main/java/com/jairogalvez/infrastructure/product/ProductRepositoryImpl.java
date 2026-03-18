package com.jairogalvez.infrastructure.product;

import com.jairogalvez.domain.product.Product;
import com.jairogalvez.domain.product.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductRepositoryBase productRepositoryBase;

    @Inject
    public ProductRepositoryImpl(ProductRepositoryBase productEntityResource) {
        this.productRepositoryBase = productEntityResource;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepositoryBase.findByIdOptional(id)
                .map(this::mapToProduct);
    }

    @Override
    @Transactional
    public Optional<Product> update(UUID id, Product product) {
        return productRepositoryBase.findByIdOptional(id)
                .map(entity -> {
                    entity.name = product.getName();
                    entity.price = product.getPrice();
                    entity.stock = product.getStock();
                    return entity;
                })
                .map(this::mapToProduct);
    }

    @Override
    @Transactional
    public Optional<Product> create(Product product) {
        ProductEntity entity = mapToEntity(product);
        entity.id = null;
        productRepositoryBase.persistAndFlush(entity);
        return Optional.of(mapToProduct(entity));
    }

    @Override
    @Transactional
    public Optional<Boolean> delete(UUID id) {
        return Optional.of(productRepositoryBase.deleteById(id));
    }

    @Override
    public Optional<List<Product>> getAll() {
        return Optional.of(productRepositoryBase.listAll()
                .stream()
                .map(this::mapToProduct)
                .toList());
    }

    private Product mapToProduct(ProductEntity entity) {
        return new Product(entity.id, entity.name, entity.price, entity.stock, entity.createdAt);
    }

    private ProductEntity mapToEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getCreatedAt());
    }
}
