// This is a category service class which we autowired with CategoryRepo so
// we can use its method to save data, fetch data by id etc

package com.example.bookProjectTekSystem.service;


import com.example.bookProjectTekSystem.model.Category;
import com.example.bookProjectTekSystem.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

// get all category  by using meethod findAll() from category Repo.
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }


    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void removeCategoryById(int id){
        categoryRepository.deleteById(id);
    }
    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }
}
