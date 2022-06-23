package com.example.bookProjectTekSystem.controller;

import com.example.bookProjectTekSystem.global.GlobalData;
import com.example.bookProjectTekSystem.model.Cart;

import com.example.bookProjectTekSystem.model.Product;

import com.example.bookProjectTekSystem.service.CartService;

import com.example.bookProjectTekSystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    private final String UNPAID = "UNPAID";
    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    // create a new cart. if there is an existing cart then its added item in the cart and if its not then
//    make new cart.
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
        BigDecimal amount = new BigDecimal(GlobalData.cart.stream().mapToDouble(Product::getPrice).sum()).setScale(2, RoundingMode.HALF_UP);
        model.addAttribute("total", amount);
        model.addAttribute("cart", GlobalData.cart);
        if (GlobalData.checkoutCart != null) {
            model.addAttribute("cartId", GlobalData.checkoutCart.getId());
        }
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index) {
//        get the cart id by calling the method getCartById and store it in the cart
        Optional<Cart> cart = cartService.getCartById(GlobalData.cartId);

//        get the product which you want to remove  and passed its index
        Product pro = GlobalData.cart.get(index);
//        if cartId is present
        if (cart.isPresent()) {
//            get the object of cart
            Cart cartObject = cart.get();
//            get the product ids and store in the variable called product Id
            String productIds = cartObject.getProductIds();
//            convert the product id in the list and seperate it with comma
            List<String> productList = Arrays.asList(productIds.split(","));

            String newProductIds = "";
            if (!CollectionUtils.isEmpty(productList)) {
                for (String product : productList) {
                    if (pro != null && !String.valueOf(pro.getId()).equals(product)) {
                        if (newProductIds != null) {
                            newProductIds += "," + product;
                        } else {
                            newProductIds = product;
                        }
                    }
                }
            }
            cartObject.setProductIds(newProductIds);
            cartService.addCart(cartObject);
        }
        GlobalData.cart.remove(index);
        return "redirect:/cart";

    }

    @GetMapping("/checkout/{cartId}")
    public String checkout(@PathVariable int cartId, Model model) {
        BigDecimal amount = new BigDecimal(GlobalData.cart.stream().mapToDouble(Product::getPrice).sum()).setScale(2, RoundingMode.HALF_UP);
        model.addAttribute("total", amount);
        model.addAttribute("cartId", GlobalData.cartId);
        return "checkout";
    }

}
