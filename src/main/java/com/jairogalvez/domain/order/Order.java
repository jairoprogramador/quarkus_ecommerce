package com.jairogalvez.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<Item> items = new ArrayList<>();
    private Double total = 0.0;
    private Date createdAt;

    public Order() {
        this.createdAt = new Date();
    }

    public Order(UUID id, Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.calculateTotal();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {}

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.calculateTotal();
    }

    public Double getTotal() {
        return total;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void addItem(Item item) {
        this.items.add(item);
        this.calculateTotal();
    }

    private void calculateTotal(){
        double total = 0.0;
        for(Item item : items){
            total+=item.getProduct().getPrice()* item.getQuantity();
        }
        this.total = total;
    }
}
