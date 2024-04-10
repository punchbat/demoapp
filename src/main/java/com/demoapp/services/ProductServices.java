package com.demoapp.services;

import com.demoapp.dto.ProductDTO;
import com.demoapp.models.Product;
import com.demoapp.repository.ProductRepository;

import java.util.List;

public class ProductServices {
    private ProductRepository productRepository = new ProductRepository();

    public List<ProductDTO> listProducts() {
        return ProductDTO.toProductDTOList(productRepository.listAll());
    }

    public ProductDTO getProduct(int id) {
            return new ProductDTO(productRepository.get(id));
    }

    public ProductDTO addProduct(Product product) {
        int newId = productRepository.add(product);

        return new ProductDTO(productRepository.get(newId));
    }

    public ProductDTO updateProduct( int id, Product product) {
        product.setId(id);

        if (productRepository.update(product)) {
            return new ProductDTO(productRepository.get(product.getId()));
        }

        return null;
    }

    public ProductDTO deleteProduct(int id) {
        return new ProductDTO(productRepository.delete(id));
    }
}
