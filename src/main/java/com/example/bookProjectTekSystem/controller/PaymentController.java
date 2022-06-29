package com.example.bookProjectTekSystem.controller;


import com.example.bookProjectTekSystem.context.GlobalContext;
import com.example.bookProjectTekSystem.model.Cart;
import com.example.bookProjectTekSystem.model.Book;
import com.example.bookProjectTekSystem.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private CartService cartService;

    private final String PAID = "PAID";

    @PostMapping("/payNow/{cartId}")
    public String Pay(@PathVariable int cartId) {
        Cart cart = cartService.getCartById(cartId).get();
        cart.setStatus(PAID);
        cartService.addCart(cart);
        return "redirect:/checkoutsuccess";
    }

    @GetMapping("/checkoutsuccess")
    public String success(Model model) {
        model.addAttribute("cartCount", GlobalContext.cart.size());
        model.addAttribute("total", GlobalContext.cart.stream().mapToDouble(Book::getPrice).sum());
        List<Book> ids = new ArrayList<>();
        for (Book book : GlobalContext.cart) {
            ids.add(book);
        }
        model.addAttribute("cart", ids);
        GlobalContext.cart.clear();
        GlobalContext.cartId = 0;
        return "checkoutsuccess";
    }
}
