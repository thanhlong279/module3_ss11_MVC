package com.example.mvcmanagementproduct.services;

import com.example.mvcmanagementproduct.models.Product;

import java.util.List;

public interface IProductService {

    void addProduct(Product product);

    List<Product> findAll();

    Product findById(int id);

    void remove(int id);

    void update(int id, Product product);

    List<Product> findByName(String name);
}
