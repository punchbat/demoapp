package com.demoapp.dto;

import com.demoapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
    private int id;
    private String name;
    private float price;

    public static List<ProductDTO> toProductDTOList (List<Product> productList) {
        List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
        for (Product product : productList) {
            productDTOList.add(new ProductDTO(product));
        }
        return productDTOList;
    }

    public ProductDTO() {}

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public ProductDTO(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
