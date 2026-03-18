package com.jairogalvez.application.product;

public record ProductDTO(
        String id,
        String name,
        Double price,
        Integer stock,
        String createAt
){}