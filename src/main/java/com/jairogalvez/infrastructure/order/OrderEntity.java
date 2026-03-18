package com.jairogalvez.infrastructure.order;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<ItemEntity> items = new ArrayList<>();
    public Double total = 0.0;
    @Column(name = "created_at")
    public Date createdAt;

    public OrderEntity() { }

    public OrderEntity(Double total) {
        this.createdAt = new Date();
        this.total = total;
    }

    public void addItem(ItemEntity item) {
        item.order = this;
        this.items.add(item);
    }
}