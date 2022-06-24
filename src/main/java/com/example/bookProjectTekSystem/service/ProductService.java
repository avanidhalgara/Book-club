package com.example.bookProjectTekSystem.service;


import com.example.bookProjectTekSystem.model.Product;
import com.example.bookProjectTekSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);

    }

    public void removeByProductId(long id) {
        productRepository.deleteById((id));

    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    //    This method will use when user logged in and see all products by category
    public List<Product> getAllProductsByCategoryId(int id) {
        return productRepository.findAllByCategory_Id(id);
    }


}
