package com.example.bookProjectTekSystem.controller;


import com.example.bookProjectTekSystem.global.GlobalData;
import com.example.bookProjectTekSystem.model.Cart;
import com.example.bookProjectTekSystem.model.Product;
import com.example.bookProjectTekSystem.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.bookProjectTekSystem.global.GlobalData.cart;

@Controller
public class PaymentController {

    @Autowired
    private CartService cartService;

    private final String PAID = "PAID";

    @PostMapping("/payNow/{cartId}")
    public String Pay(@PathVariable int cartId){
        Cart cart = cartService.getCartById(cartId).get();
        cart.setStatus(PAID);
        cartService.addCart(cart);
        return "redirect:/checkoutsuccess";
    }

    @GetMapping("/checkoutsuccess")
    public String success(Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        List<Product> ids = new ArrayList<>();
        for(Product product : GlobalData.cart){
            ids.add(product);
        }
        model.addAttribute("cart",ids);
        GlobalData.cart.clear();
        return "checkoutsuccess";
    }
}
