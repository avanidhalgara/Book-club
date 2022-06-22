package com.example.bookProjectTekSystem.model;

// category entity


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private int id;

    private String name;

    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

}
