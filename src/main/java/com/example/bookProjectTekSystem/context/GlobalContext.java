package com.example.bookProjectTekSystem.context;

import com.example.bookProjectTekSystem.model.Cart;
import com.example.bookProjectTekSystem.model.Book;

import java.util.ArrayList;
import java.util.List;

public class GlobalContext {

    public static List<Book> cart;
    public static long cartId;

    public static int userId;

    public static Cart checkoutCart;


    static {
        cart = new ArrayList<Book>();
    }
}
