package com.example.bookProjectTekSystem.global;

import com.example.bookProjectTekSystem.model.Cart;
import com.example.bookProjectTekSystem.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    public static List<Product> cart;
    public static long cartId;

    public static int userId;

    public static Cart checkoutCart;


    static {
        cart = new ArrayList<Product>();
    }
}
