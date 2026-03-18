package com.jairogalvez.application.product;

import com.jairogalvez.domain.product.Product;
import com.jairogalvez.domain.product.ProductRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("products")
public class ProductController {

    private final ProductRepository productRepository;

    @Inject
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public ProductDTO create(ProductDTO productDto) {
        Product product = new Product(productDto.name(), productDto.price(), productDto.stock());
        return productRepository.create(product)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Product not created"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDTO> findAll() {
        return productRepository.getAll()
                .map(list -> {
                    return list.stream().map(this::mapToDTO).toList();
                })
                .orElseThrow(() -> new RuntimeException("Products not created"));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductDTO findById(@PathParam("id") UUID id) {
        return productRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductDTO updateById(@PathParam("id") UUID id, ProductDTO productDto) {
        Product product = new Product(productDto.name(), productDto.price(), productDto.stock());
        return productRepository.update(id, product)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Product not updated"));
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") UUID id) {
        boolean deleted = productRepository.delete(id)
                .orElse(false);

        return deleted
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(
                product.getId().toString(), product.getName(),
                product.getPrice(), product.getStock(),
                product.getCreatedAt().toString());
    }
}
