package com.jairogalvez.application.order;

import com.jairogalvez.domain.order.Item;
import com.jairogalvez.domain.order.Order;
import com.jairogalvez.domain.order.OrderRepository;
import com.jairogalvez.domain.product.Product;
import com.jairogalvez.domain.product.ProductRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Inject
    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public OrderDTO create(OrderDTO orderDto) {
        return orderRepository.create(this.mapToDomain(orderDto))
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Product not created"));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderDTO findById(@PathParam("id") UUID id) {
        return orderRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private OrderDTO mapToDTO(Order order) {
        List<ItemDTO> items = new ArrayList<>();
        for (Item item : order.getItems()) {
            ItemDTO itemDTO = new ItemDTO(item.getProduct().getId().toString(), item.getQuantity());
            items.add(itemDTO);
        }
        return new OrderDTO(
                order.getId().toString(), order.getTotal(),
                order.getCreatedAt(), items);
    }

    private Order mapToDomain(OrderDTO orderDto) throws RuntimeException {
        List<Item> items = new ArrayList<>();
        for (ItemDTO itemDto : orderDto.items()) {
            Optional<Product> productOp = productRepository.findById(UUID.fromString(itemDto.product_id()));
            if (productOp.isEmpty()) {
                throw new RuntimeException("Product not found");
            }
            Item item = new Item(productOp.get(), itemDto.quantity());
            items.add(item);
        }
        Order order = new Order();
        order.setItems(items);
        return order;
    }
}
