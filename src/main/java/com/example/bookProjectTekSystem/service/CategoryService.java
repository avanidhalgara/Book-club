// This is a category service class which we autowired with CategoryRepo so
// we can use its method to save data, fetch data by id etc

package com.example.bookProjectTekSystem.service;


import com.example.bookProjectTekSystem.model.Category;
import com.example.bookProjectTekSystem.model.Product;
import com.example.bookProjectTekSystem.repository.CategoryRepository;
import com.example.bookProjectTekSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

// get all category  by using meethod findAll() from category Repo.
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

// adding category and save it by calling method save from category repo.
    public void addCategory(Category category){
        categoryRepository.save(category);
    }


//    delete category by id and called method delete by id to delete category
    public void removeCategoryById(int id){
        List<Product> products = productRepository.findAllByCategory_Id(id);
        if(!CollectionUtils.isEmpty(products)){
            List<Long> productIds = new ArrayList<>();
            for(Product product : products ){
                productIds.add(product.getId());
            }
            productRepository.deleteAllById(productIds);
        }
        categoryRepository.deleteById(id);

    }

//
    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }
}
