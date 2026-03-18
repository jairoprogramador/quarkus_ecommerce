package com.jairogalvez.domain.product;

import java.util.Date;
import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private Double price;
    private Integer stock;
    private Date createdAt;

    public Product(String name, Double price, Integer stock) {
        validatePrice(price);
        validateStock(stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = new Date();
    }

    public Product(UUID id, String name, Double price, Integer stock, Date createdAt) {
        validatePrice(price);
        validateStock(stock);
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void validatePrice(Double price){
        if(price <= 0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }

    public void validateStock(Integer stock){
        if(stock < 0){
            throw new IllegalArgumentException("Stock must be greater than or equal 0");
        }
    }
}
