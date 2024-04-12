package com.demoapp.service;

import com.demoapp.dto.request.AddProductRequestDTO;
import com.demoapp.dto.request.UpdateProductRequestDTO;
import com.demoapp.dto.response.ProductResponseDTO;
import com.demoapp.entity.ProductEntity;
import com.demoapp.dao.ProductDAO;
import com.demoapp.exception.DatabaseException;
import com.demoapp.exception.NotFoundException;
import com.demoapp.middleware.LoggingFilter;
import com.demoapp.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.UUID;

public class ProductService {

    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    private ProductDAO productDAO;

    public ProductService() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        this.productDAO = new ProductDAO(session);
    }

    public List<ProductResponseDTO> listProducts() {
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":start");
        List<ProductEntity> products = productDAO.findAll();
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":end");
        return ProductResponseDTO.toProductDTOList(products);
    }

    public ProductResponseDTO getProduct(String id) {
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":start");
        ProductEntity product = productDAO.find(UUID.fromString(id));
        if (product == null) {
            Error err = new Error("Product not found with ID: " + id);
            throw new NotFoundException(HttpURLConnection.HTTP_NOT_FOUND, err, err.getMessage());
        }
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":end");
        return new ProductResponseDTO(product);
    }

    public void addProduct(AddProductRequestDTO addProductRequestDTO) {
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":start");
        ProductEntity product = ProductEntity
                .builder()
                .name(addProductRequestDTO.getName())
                .description(addProductRequestDTO.getDescription())
                .price(addProductRequestDTO.getPrice())
                .build();

        try {
            productDAO.save(product);
            logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":added new product");
        } catch (Exception e) {
            Error err = new Error("Error saving product to the database");
            throw new DatabaseException(HttpURLConnection.HTTP_BAD_REQUEST, err, err.getMessage());
        }
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":end");
    }

    public ProductResponseDTO updateProduct(UpdateProductRequestDTO updateProductRequestDTO) {
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":start");
        ProductEntity existingProduct = productDAO.find(UUID.fromString(updateProductRequestDTO.getId()));
        if (existingProduct == null) {
            Error err = new Error("Product not found with ID: " + updateProductRequestDTO.getId());
            throw new NotFoundException(HttpURLConnection.HTTP_NOT_FOUND, err, err.getMessage());
        }

        String name = updateProductRequestDTO.getName();
        if (name != null && !name.isEmpty()) {
            existingProduct.setName(name);
        }

        String description = updateProductRequestDTO.getDescription();
        if (description != null && !description.isEmpty()) {
            existingProduct.setDescription(description);
        }

        ProductEntity updatedProduct;
        try {
            updatedProduct = productDAO.update(existingProduct);
            logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":updated product");
        } catch (Exception e) {
            Error err = new Error("Error updating product");
            throw new DatabaseException(HttpURLConnection.HTTP_BAD_REQUEST, err, err.getMessage());
        }
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":end");
        return new ProductResponseDTO(updatedProduct);
    }

    public void deleteProduct(String id) {
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":start");
        ProductEntity product = productDAO.find(UUID.fromString(id));
        if (product == null) {
            Error err = new Error("Product not found with ID: " + id);
            throw new NotFoundException(HttpURLConnection.HTTP_NOT_FOUND, err, err.getMessage());
        }

        try {
            productDAO.delete(product);
            logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":deleted product");
        } catch (Exception e) {
            Error err = new Error("Error deleting product");
            throw new DatabaseException(HttpURLConnection.HTTP_BAD_REQUEST, err, err.getMessage());
        }
        logger.info(ProductService.class.getName() + ":" + new Throwable().getStackTrace()[0].getMethodName() + ":end");
    }
}
