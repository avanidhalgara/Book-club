package com.example.bookProjectTekSystem.model;

// category entity


import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private long id;

    private String bookIds;

    private double amount;

    private String status;
    private int userId;

    public Cart() {

    }

}
