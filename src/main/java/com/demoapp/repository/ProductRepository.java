package com.demoapp.repository;

import com.demoapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static List<Product> data = new ArrayList<>();

    static {
        data.add(new Product(1, "iPhone X", 999.99f));
        data.add(new Product(2, "XBOX 360", 329.50f));
    }

    public ProductRepository() {
    }

    public List<Product> listAll() {
        return new ArrayList<Product>(data);
    }

    public int add(Product product) {
        int newId = data.size() + 1;
        product.setId(newId);
        data.add(product);

        return newId;
    }

    public Product get(int id) {
        Product productToFind = new Product(id);
        int index = data.indexOf(productToFind);
        if (index >= 0) {
            return data.get(index);
        }
        return null;
    }

    public Product delete(int id) {
        Product productToFind = new Product(id);
        int index = data.indexOf(productToFind);
        if (index >= 0) {
            Product deletedProduct = data.remove(index);
            return deletedProduct;
        }
        return null;
    }

    public boolean update(Product product) {
        int index = data.indexOf(product);
        if (index >= 0) {
            data.set(index, product);
            return true;
        }
        return false;
    }
}
