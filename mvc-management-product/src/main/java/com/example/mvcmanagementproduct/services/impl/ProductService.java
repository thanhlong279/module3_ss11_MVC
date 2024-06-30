package com.example.mvcmanagementproduct.services.impl;

import com.example.mvcmanagementproduct.models.Product;
import com.example.mvcmanagementproduct.repositories.ProductRepository;
import com.example.mvcmanagementproduct.services.IProductService;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService implements IProductService {
    private final ProductRepository productRepository = ProductRepository.getInstance();
    private static ProductService instance;

    private ProductService() {
    }

    public synchronized static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.add(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.getAll();
    }

    @Override
    public Product findById(int id) {
        List<Product> products = productRepository.getAll();
        return products.get(id);
    }

    @Override
    public void remove(int id) {
        productRepository.remove(id);
    }

    @Override
    public void update(int id, Product product) {
        productRepository.update(id, product);
    }

        @Override
        public List<Product> findByName(String name) {
        List<Product> products = productRepository.getAll();
            return products.stream()
                    .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

}
