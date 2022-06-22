package com.example.bookProjectTekSystem.controller;

import com.example.bookProjectTekSystem.global.GlobalData;
import com.example.bookProjectTekSystem.model.Cart;
import com.example.bookProjectTekSystem.model.Product;
import com.example.bookProjectTekSystem.model.User;
import com.example.bookProjectTekSystem.service.CartService;
import com.example.bookProjectTekSystem.service.CategoryService;
import com.example.bookProjectTekSystem.service.CustomUserDetailService;
import com.example.bookProjectTekSystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    CartService cartService;
    @Autowired
    private CustomUserDetailService userService;

    //    @GetMapping({"/","/home"})
//    public String home(Model model){
//        return "index";
//    }
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        getUserFromContext();
        populateUnpaidCart(model);
        return "index";
    }

    private void populateUnpaidCart(Model model) {
        if (GlobalData.userId != 0) {
            Cart cart = cartService.getCartByUserId(GlobalData.userId);
            GlobalData.cart.clear();
            if (cart != null) {
                GlobalData.checkoutCart = cart;
                GlobalData.cartId = cart.getId();
                String productIds = cart.getProductIds();
                List<String> productList = Arrays.asList(productIds.split(","));
                if (!CollectionUtils.isEmpty(productList)) {
                    for (String productString : productList) {
                        Optional<Product> product = productService.getProductById(Long.parseLong(productString));
                        if (product != null) {
                            GlobalData.cart.add(product.get());
                            model.addAttribute("cartCount", GlobalData.cart.size());
                        }
                    }
                }
            }
        }
    }

    private void getUserFromContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if (username != null) {
                User user = userService.loadUser(username).get();
                if (user != null) {
                    int userId = user.getId();
                    GlobalData.userId = userId;
                }
            }
        }
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable int id) {
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "viewProduct";
    }
}
