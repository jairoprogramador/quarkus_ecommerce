package com.jairogalvez.infrastructure.order;

import com.jairogalvez.domain.order.Item;
import com.jairogalvez.domain.order.Order;
import com.jairogalvez.domain.order.OrderRepository;
import com.jairogalvez.domain.product.Product;
import com.jairogalvez.infrastructure.product.ProductEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderRepositoryBase orderRepositoryBase;

    @Inject
    public OrderRepositoryImpl(OrderRepositoryBase orderRepositoryBase) {
        this.orderRepositoryBase = orderRepositoryBase;
    }

    @Override
    @Transactional
    public Optional<Order> create(Order order) {
        OrderEntity orderEntity = this.mapToEntity(order);
        orderRepositoryBase.persistAndFlush(orderEntity);
        return Optional.of(this.mapToOrder(orderEntity));
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepositoryBase.findByIdOptional(id)
                .map(this::mapToOrder);
    }

    @Override
    public Optional<Order> update(UUID id, Order order) {
        return orderRepositoryBase.findByIdOptional(id)
                .map(entity -> {
                    entity.items.clear();
                    return this.mapToEntity(entity, order);
                })
                .map(this::mapToOrder);
    }

    private Order mapToOrder(OrderEntity entity) {
        Order order = new Order(entity.id, entity.createdAt);
        for(ItemEntity item : entity.items){
            order.addItem(this.mapToItem(item));
        }
        return order;
    }

    private OrderEntity mapToEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity(order.getTotal());
        return this.mapToEntity(orderEntity, order);
    }

    private OrderEntity mapToEntity(OrderEntity orderEntity, Order order) {
        orderEntity.total = order.getTotal();
        for(Item item : order.getItems()) {
            ProductEntity productEntity = this.mapToProductEntity(item.getProduct());

            ItemEntity itemEntity = new ItemEntity();
            itemEntity.producto = productEntity;
            itemEntity.quantity = item.getQuantity();

            orderEntity.addItem(itemEntity);
        }
        return orderEntity;
    }

    private ProductEntity mapToProductEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.id = product.getId();
        productEntity.name = product.getName();
        productEntity.price = product.getPrice();
        productEntity.stock = product.getStock();
        productEntity.createdAt = product.getCreatedAt();
        return productEntity;
    }

    private Item mapToItem(ItemEntity itemEntity) {
        ProductEntity productEntity = itemEntity.producto;
        Product product = new Product(
                productEntity.id, productEntity.name, productEntity.price,
                productEntity.stock, productEntity.createdAt);

        return new Item(product, itemEntity.quantity);
    }

}
