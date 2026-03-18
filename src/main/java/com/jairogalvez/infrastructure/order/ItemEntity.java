package com.jairogalvez.infrastructure.order;

import com.jairogalvez.infrastructure.product.ProductEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "items")
public class ItemEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    public ProductEntity producto;

    public Integer quantity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    public OrderEntity order;

    public ItemEntity() { }

}
