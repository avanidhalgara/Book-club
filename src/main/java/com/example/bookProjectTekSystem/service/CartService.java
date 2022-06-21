package com.example.bookProjectTekSystem.service;

import com.example.bookProjectTekSystem.model.Cart;
import com.example.bookProjectTekSystem.model.Category;
import com.example.bookProjectTekSystem.model.Product;
import com.example.bookProjectTekSystem.repository.CartRepository;
import com.example.bookProjectTekSystem.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public Optional<Cart> getCartById(long id){
        return cartRepository.findById(id);
    }
    public void addCart(Cart cart){
        cartRepository.save(cart);

    }
    public Cart getCartByUserId(int userId){
        return cartRepository.findAllByUserIdAndStatus(userId, "UNPAID");
    }


}
