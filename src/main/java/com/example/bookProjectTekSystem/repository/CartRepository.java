package com.example.bookProjectTekSystem.repository;

import com.example.bookProjectTekSystem.model.Cart;
import com.example.bookProjectTekSystem.model.Category;
import com.example.bookProjectTekSystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findAllByUserIdAndStatus(int userId, String status);

}
