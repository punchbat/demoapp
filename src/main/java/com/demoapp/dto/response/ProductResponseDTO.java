package com.demoapp.dto.response;

import com.demoapp.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private float price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static List<ProductResponseDTO> toProductDTOList (List<ProductEntity> products) {
        return products.stream().map(ProductResponseDTO::new).collect(Collectors.toList());
    }

    public ProductResponseDTO(ProductEntity product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
