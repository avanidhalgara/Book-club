package com.example.bookProjectTekSystem.controller;

import com.example.bookProjectTekSystem.context.GlobalContext;
import com.example.bookProjectTekSystem.helper.Message;
import com.example.bookProjectTekSystem.model.Cart;

import com.example.bookProjectTekSystem.model.Book;

import com.example.bookProjectTekSystem.service.CartService;

import com.example.bookProjectTekSystem.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    Logger logger = LoggerFactory.getLogger(CartController.class);

    private final String UNPAID = "UNPAID";
    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    // create a new cart. if there is an existing cart then its added item in the cart and if its not then
//    make new cart.
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id, Model model, HttpSession session) {
        try {
            GlobalContext.cart.add(bookService.getBookById(id).get());
            Cart cart;
            if (GlobalContext.cartId != 0) {
                cart = cartService.getCartById(GlobalContext.cartId).get();
                cart.setBookIds(cart.getBookIds() + "," + id);
            } else {
                cart = new Cart();
                cart.setAmount(GlobalContext.cart.stream().mapToDouble(Book::getPrice).sum());
                cart.setStatus(UNPAID);
                String bookIds = String.valueOf(id);
                cart.setBookIds(bookIds);
                cart.setUserId(GlobalContext.userId);
            }
            cartService.addCart(cart);
            GlobalContext.checkoutCart = cart;
            GlobalContext.cartId = cart.getId();
            return "redirect:/cart";
        } catch (Exception e) {
            logger.error("Add to cart failed.", e.getMessage());
            session.setAttribute("message", new Message("Add to cart failed.", "alert-danger"));
            return "redirect:/cart";
        }
    }

    @GetMapping("/cart")
    public String cartGet(Model model, HttpSession session) {
        try {
            model.addAttribute("cartCount", GlobalContext.cart.size());
            BigDecimal amount = new BigDecimal(GlobalContext.cart.stream().mapToDouble(Book::getPrice).sum()).setScale(2, RoundingMode.HALF_UP);
            model.addAttribute("total", amount);
            model.addAttribute("cart", GlobalContext.cart);
            if (GlobalContext.checkoutCart != null) {
                model.addAttribute("cartId", GlobalContext.checkoutCart.getId());
            }
            return "cart";
        } catch (Exception e) {
            logger.error("Get cart failed.", e.getMessage());
            session.setAttribute("message", new Message("Get to cart failed.", "alert-danger"));
            return "cart";
        }
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index) {
//        get the cart id by calling the method getCartById and store it in the cart
        Optional<Cart> cart = cartService.getCartById(GlobalContext.cartId);

//        get the product which you want to remove  and passed its index
        Book pro = GlobalContext.cart.get(index);
//        if cartId is present
        if (cart.isPresent()) {
//            get the object of cart
            Cart cartObject = cart.get();
//            get the product ids and store in the variable called product Id
            String bookIds = cartObject.getBookIds();
//            convert the product id in the list and seperate it with comma
            List<String> bookList = Arrays.asList(bookIds.split(","));

            String newBookIds = "";
            if (!CollectionUtils.isEmpty(bookList)) {
                for (String book : bookList) {
                    if (pro != null && !String.valueOf(pro.getId()).equals(book)) {
                        if (newBookIds != null) {
                            newBookIds += "," + book;
                        } else {
                            newBookIds = book;
                        }
                    }
                }
            }
            cartObject.setBookIds(newBookIds);
            cartService.addCart(cartObject);
        }
        GlobalContext.cart.remove(index);
        return "redirect:/cart";

    }

    @GetMapping("/checkout/{cartId}")
    public String checkout(@PathVariable int cartId, Model model) {
        BigDecimal amount = new BigDecimal(GlobalContext.cart.stream().mapToDouble(Book::getPrice).sum()).setScale(2, RoundingMode.HALF_UP);
        model.addAttribute("total", amount);
        model.addAttribute("cartId", GlobalContext.cartId);
        return "checkout";
    }

}
