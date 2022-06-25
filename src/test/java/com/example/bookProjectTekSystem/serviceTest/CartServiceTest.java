package com.example.bookProjectTekSystem.serviceTest;


import com.example.bookProjectTekSystem.model.Cart;

import com.example.bookProjectTekSystem.repository.CartRepository;

import com.example.bookProjectTekSystem.repository.UserRepository;
import com.example.bookProjectTekSystem.service.CartService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {


    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;


    @Test
    public void getCartByIdTest() {
//        Optional<Cart> cart = Optional.ofNullable(new Cart());
        Cart cart = createCartObject();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        Cart cartResponse = cartService.getCartById(1L).get();
        assertEquals("UNPAID", cartResponse.getStatus());
        assertEquals(3.25, cartResponse.getAmount());
        assertEquals(1, cartResponse.getId());
    }

    private Cart createCartObject() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus("UNPAID");
        cart.setAmount(3.25);
        cart.setUserId(1);
        return cart;
    }

    @Test
    public void getCartByUserIdTest() {
        Cart cart = createCartObject();
        when(cartRepository.findAllByUserIdAndStatus(1, "UNPAID")).thenReturn(cart);
        Cart cartResponse = cartService.getCartByUserId(1);
        assertEquals(1, cartResponse.getUserId());
        assertEquals("UNPAID", cartResponse.getStatus());
    }

    @Test
    public void addCart() {
        Cart cart = createCartObject();
        cartService.addCart(cart);
        verify(cartRepository, times(1)).save(cart);
    }
}
