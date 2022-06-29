package com.example.bookProjectTekSystem.controller;

import com.example.bookProjectTekSystem.context.GlobalContext;
import com.example.bookProjectTekSystem.model.Cart;
import com.example.bookProjectTekSystem.model.Book;
import com.example.bookProjectTekSystem.model.User;
import com.example.bookProjectTekSystem.service.CartService;
import com.example.bookProjectTekSystem.service.CategoryService;
import com.example.bookProjectTekSystem.service.CustomUserDetailService;
import com.example.bookProjectTekSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Autowired
    CategoryService categoryService;
    @Autowired
    BookService bookService;
    @Autowired
    CartService cartService;
    @Autowired
    private CustomUserDetailService userService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        getUserFromContext(model);
        populateUnpaidCart(model);
        return "index";
    }

    //   made method to check if there is any unpaid cart
    private void populateUnpaidCart(Model model) {
        if (GlobalContext.userId != 0) {
            //Load unpaid (left from last session) cart from DB
            Cart cart = cartService.getCartByUserId(GlobalContext.userId);
            GlobalContext.cart.clear();
            GlobalContext.cartId = 0;
            if (cart != null) {
                // If cart is found save it as Checkout Cart
                GlobalContext.checkoutCart = cart;
                GlobalContext.cartId = cart.getId();
                // Get comma separated productIds from cart (model) object
                String bookIds = cart.getBookIds();
                // Convert comma separated productIds to list
                if (bookIds != null) {
                    List<String> bookList = Arrays.asList(bookIds.split(","));
                    if (!CollectionUtils.isEmpty(bookList)) {
                        for (String bookString : bookList) {
                            if (!bookString.isEmpty() && bookString != null) {
                                // For each productId get the product (model) object and save it to Global.cart list
                                Optional<Book> book = bookService.getBookById(Long.parseLong(bookString));
                                if (book.isPresent()) {
                                    GlobalContext.cart.add(book.get());
                                }
                            }
                        }
                        model.addAttribute("cartCount", GlobalContext.cart.size());
                    }
                }
            }
        }
    }

    // to get the user id from context
    private void getUserFromContext(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if (username != null) {
                User user = userService.loadUser(username).get();
                if (user != null) {
                    int userId = user.getId();
                    GlobalContext.userId = userId;
                    model.addAttribute("Role", user.getRoles().get(0).getName());
                    model.addAttribute("UserName", user.getFirstName());
                }
            }
        }
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("books", bookService.getAllBook());
        model.addAttribute("cartCount", GlobalContext.cart.size());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("books", bookService.getAllBooksByCategoryId(id));
        model.addAttribute("cartCount", GlobalContext.cart.size());
        return "shop";
    }

    @GetMapping("/shop/book/{id}")
    public String viewBook(Model model, @PathVariable int id) {
        model.addAttribute("book", bookService.getBookById(id).get());
        model.addAttribute("cartCount", GlobalContext.cart.size());
        return "viewBook";
    }
}
