package com.example.bookProjectTekSystem.repository;

import com.example.bookProjectTekSystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //    declare a custom method to find all products by category id
    List<Product> findAllByCategory_Id(int id);
}
