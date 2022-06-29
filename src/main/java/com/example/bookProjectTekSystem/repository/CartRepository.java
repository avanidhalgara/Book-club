package com.example.bookProjectTekSystem.repository;

import com.example.bookProjectTekSystem.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findAllByUserIdAndStatus(int userId, String status);

}
