package com.example.bookProjectTekSystem.controller;

import com.example.bookProjectTekSystem.global.GlobalData;
import com.example.bookProjectTekSystem.model.Cart;

import com.example.bookProjectTekSystem.model.Product;

import com.example.bookProjectTekSystem.service.CartService;

import com.example.bookProjectTekSystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class CartController {
    private final String UNPAID = "UNPAID";
    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;


    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id) {
        GlobalData.cart.add(productService.getProductById(id).get());
        Cart cart;


        if (GlobalData.cartId != 0) {
            cart = cartService.getCartById(GlobalData.cartId).get();
            cart.setProductIds(cart.getProductIds() + "," + id);
        } else {
            cart = new Cart();
            cart.setAmount(GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
            cart.setStatus(UNPAID);
            String productIds = String.valueOf(id);
            cart.setProductIds(productIds);
            cart.setUserId(GlobalData.userId);
        }
        cartService.addCart(cart);
        GlobalData.checkoutCart = cart;
        GlobalData.cartId = cart.getId();
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cartGet(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        if (GlobalData.checkoutCart != null) {
            model.addAttribute("cartId", GlobalData.checkoutCart.getId());
        }
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index) {
        GlobalData.cart.remove(index);
        return "redirect:/cart";

    }

    @GetMapping("/checkout/{cartId}")
    public String checkout(@PathVariable int cartId, Model model) {
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cartId", GlobalData.cartId);
        return "checkout";
    }

}
