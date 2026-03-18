package com.jairogalvez.infrastructure.product;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;
    public String name;
    public Double price;
    public Integer stock;
    @Column(name = "created_at")
    public Date createdAt;

    public ProductEntity() {}

    public ProductEntity(UUID id, String name, Double price, Integer stock, Date createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
    }
}