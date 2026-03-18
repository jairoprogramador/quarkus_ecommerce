package com.jairogalvez.domain.order;

import com.jairogalvez.domain.product.Product;

public class Item {
    private Integer quantity;
    private Product product;

    public Item(Product product, Integer quantity) {
        this.validateStock(product.getStock(), quantity);
        this.product = product;
        this.quantity = quantity;
        this.product.setStock(product.getStock() - quantity);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void validateStock(Integer stock, Integer quantity){
        if(stock <= 0){
            throw new IllegalArgumentException("Not enough stock for this order");
        }
        if(quantity <= 0){
            throw new IllegalArgumentException("Negative quantities of products are not accepted");
        }
        if (stock < quantity){
            throw new IllegalArgumentException("There is not enough stock");
        }
    }

}
