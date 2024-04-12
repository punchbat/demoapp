package com.demoapp.dao;

import com.demoapp.entity.ProductEntity;
import org.hibernate.Session;

public class ProductDAO extends GenericDAO<ProductEntity> {
    public ProductDAO(Session session) {
        super(session);
    }
}